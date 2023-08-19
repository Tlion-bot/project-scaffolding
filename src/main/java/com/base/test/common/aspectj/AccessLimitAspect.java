package com.base.test.common.aspectj;


import com.base.test.common.annotation.RateLimit;
import com.base.test.common.exception.CustomException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * 切面类：实现限流校验
 *
 *
 * @author
 */
@Aspect
@Component
public class AccessLimitAspect {

    @Resource
    private RedisTemplate<String, Integer> redisTemplate;

    /**
     * 这里我们使用注解的形式
     * 当然，我们也可以通过切点表达式直接指定需要拦截的package,需要拦截的class 以及 method
     */
    @Pointcut("@annotation(com.base.test.common.annotation.RateLimit)")
    public void limitPointCut() {
    }

    /**
     * 环绕通知
     */
    @Around("limitPointCut()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        // 获取被注解的方法
        MethodInvocationProceedingJoinPoint mjp = (MethodInvocationProceedingJoinPoint) pjp;
        MethodSignature signature = (MethodSignature) mjp.getSignature();
        Method method = signature.getMethod();

        // 获取方法上的注解
        RateLimit rateLimit = method.getAnnotation(RateLimit.class);
        if (rateLimit == null) {
            // 如果没有注解，则继续调用，不做任何处理
            return pjp.proceed();
        }
        /**
         * 代码走到这里，说明有 RateLimit 注解，那么就需要做限流校验了
         *  1、这里可以使用Redis的API做计数校验
         *  2、这里也可以使用Lua脚本做计数校验，都可以
         */
        //获取request对象
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 获取请求IP地址
        String ip = getIpAddr(request);
        // 请求url路径
        String uri = request.getRequestURI();
        //存到redis中的key
        String key = "RateLimit:" + ip + ":" + uri;
        // 缓存中存在key，在限定访问周期内已经调用过当前接口
        if (redisTemplate.hasKey(key)) {
            // 访问次数自增1
            redisTemplate.opsForValue().increment(key, 1);
            // 超出访问次数限制
            if (redisTemplate.opsForValue().get(key) > rateLimit.count()) {
                throw new CustomException(rateLimit.msg());
            }
            // 未超出访问次数限制，不进行任何操作，返回true
        } else {
            // 第一次设置数据,过期时间为注解确定的访问周期
            redisTemplate.opsForValue().set(key, 1, rateLimit.cycle(), TimeUnit.SECONDS);
        }
        return pjp.proceed();
    }

    //获取请求的归属IP地址
    private String getIpAddr(HttpServletRequest request) {
        String ipAddress = null;
        try {
            ipAddress = request.getHeader("x-forwarded-for");
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getRemoteAddr();
            }
            // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
            if (ipAddress != null && ipAddress.length() > 15) {
                // = 15
                if (ipAddress.indexOf(",") > 0) {
                    ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
                }
            }
        } catch (Exception e) {
            ipAddress = "";
        }
        return ipAddress;
    }
}
