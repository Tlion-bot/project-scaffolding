// package com.base.test.framework.interceptor.impl;
//
// import cn.hutool.core.io.IoUtil;
// import cn.hutool.core.lang.Validator;
// import com.jxbs.common.constant.Constants;
// import com.jxbs.common.core.redis.RedisCache;
// import com.jxbs.common.filter.RepeatedlyRequestWrapper;
// import com.jxbs.common.utils.JsonUtils;
// import com.jxbs.framework.interceptor.RepeatSubmitInterceptor;
// import lombok.extern.slf4j.Slf4j;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.stereotype.Component;
//
// import javax.servlet.http.HttpServletRequest;
// import java.io.IOException;
// import java.util.HashMap;
// import java.util.Map;
// import java.util.concurrent.TimeUnit;
//
// /**
//  * 判断请求url和数据是否和上一次相同，
//  * 如果和上次相同，则是重复提交表单。 有效时间为10秒内。
//  *
//  * @author baseWork
//  */
// @Slf4j
// @Component
// public class SameUrlDataInterceptor extends RepeatSubmitInterceptor
// {
//     public final String REPEAT_PARAMS = "repeatParams";
//
//     public final String REPEAT_TIME = "repeatTime";
//
//     // 令牌自定义标识
//     @Value("${token.header}")
//     private String header;
//
//     @Autowired
//     private RedisCache redisCache;
//
//     /**
//      * 间隔时间，单位:秒 默认10秒
//      *
//      * 两次相同参数的请求，如果间隔时间大于该参数，系统不会认定为重复提交的数据
//      */
//     private int intervalTime = 10;
//
//     public void setIntervalTime(int intervalTime)
//     {
//         this.intervalTime = intervalTime;
//     }
//
//     @SuppressWarnings("unchecked")
//     @Override
//     public boolean isRepeatSubmit(HttpServletRequest request)
//     {
//         String nowParams = "";
//         if (request instanceof RepeatedlyRequestWrapper)
//         {
//             RepeatedlyRequestWrapper repeatedlyRequest = (RepeatedlyRequestWrapper) request;
// 			try {
// 				nowParams = IoUtil.readUtf8(repeatedlyRequest.getInputStream());
// 			} catch (IOException e) {
// 				log.warn("读取流出现问题！");
// 			}
// 		}
//
//         // body参数为空，获取Parameter的数据
//         if (Validator.isEmpty(nowParams))
//         {
//             nowParams = JsonUtils.toJsonString(request.getParameterMap());
//         }
//         Map<String, Object> nowDataMap = new HashMap<String, Object>();
//         nowDataMap.put(REPEAT_PARAMS, nowParams);
//         nowDataMap.put(REPEAT_TIME, System.currentTimeMillis());
//
//         // 请求地址（作为存放cache的key值）
//         String url = request.getRequestURI();
//
//         // 唯一值（没有消息头则使用请求地址）
//         String submitKey = request.getHeader(header);
//         if (Validator.isEmpty(submitKey))
//         {
//             submitKey = url;
//         }
//
//         // 唯一标识（指定key + 消息头）
//         String cacheRepeatKey = Constants.REPEAT_SUBMIT_KEY + submitKey;
//
//         Object sessionObj = redisCache.getCacheObject(cacheRepeatKey);
//         if (sessionObj != null)
//         {
//             Map<String, Object> sessionMap = (Map<String, Object>) sessionObj;
//             if (sessionMap.containsKey(url))
//             {
//                 Map<String, Object> preDataMap = (Map<String, Object>) sessionMap.get(url);
//                 if (compareParams(nowDataMap, preDataMap) && compareTime(nowDataMap, preDataMap))
//                 {
//                     return true;
//                 }
//             }
//         }
//         Map<String, Object> cacheMap = new HashMap<String, Object>();
//         cacheMap.put(url, nowDataMap);
//         redisCache.setCacheObject(cacheRepeatKey, cacheMap, intervalTime, TimeUnit.SECONDS);
//         return false;
//     }
//
//     /**
//      * 判断参数是否相同
//      */
//     private boolean compareParams(Map<String, Object> nowMap, Map<String, Object> preMap)
//     {
//         String nowParams = (String) nowMap.get(REPEAT_PARAMS);
//         String preParams = (String) preMap.get(REPEAT_PARAMS);
//         return nowParams.equals(preParams);
//     }
//
//     /**
//      * 判断两次间隔时间
//      */
//     private boolean compareTime(Map<String, Object> nowMap, Map<String, Object> preMap)
//     {
//         long time1 = (Long) nowMap.get(REPEAT_TIME);
//         long time2 = (Long) preMap.get(REPEAT_TIME);
//         if ((time1 - time2) < (this.intervalTime * 1000))
//         {
//             return true;
//         }
//         return false;
//     }
// }
