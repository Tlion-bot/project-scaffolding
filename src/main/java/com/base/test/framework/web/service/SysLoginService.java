package com.base.test.framework.web.service;

import cn.hutool.core.util.StrUtil;
import com.base.test.common.constant.Constants;
import com.base.test.common.core.domain.entity.SysUser;
import com.base.test.common.core.domain.model.LoginBody;
import com.base.test.common.core.domain.model.LoginUser;
import com.base.test.common.core.redis.RedisCache;
import com.base.test.common.exception.CustomException;
import com.base.test.common.utils.DateUtils;
import com.base.test.common.utils.MessageUtils;
import com.base.test.common.utils.ServletUtils;
import com.base.test.framework.config.properties.CaptchaProperties;
import com.base.test.framework.security.provider.EmailCodeAuthenticationToken;
import com.base.test.framework.security.provider.WrongEmailCodeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 登录校验方法
 *
 * @author baseWork
 */
@Component
public class SysLoginService {
    @Autowired
    private TokenService tokenService;

    @Resource
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private CaptchaProperties captchaProperties;



    @Autowired
    private AsyncService asyncService;

    /**
     * 登录验证
     */
    public String login(LoginBody loginBody) {
        HttpServletRequest request = ServletUtils.getRequest();
        if (StrUtil.isNotBlank(loginBody.getCode()) && StrUtil.isNotBlank(loginBody.getUuid())) {
            if (captchaProperties.getEnabled()) {
                String verifyKey = Constants.CAPTCHA_CODE_KEY + loginBody.getUuid();
                String captcha = redisCache.getCacheObject(verifyKey);
                redisCache.deleteObject(verifyKey);
                if (captcha == null) {
                    asyncService.recordLogininfor(loginBody.getUsername(), Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.expire"), request);
                    throw new CustomException("验证码已失效");
                }
                if (!loginBody.getCode().equalsIgnoreCase(captcha)) {
                    asyncService.recordLogininfor(loginBody.getUsername(), Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.error"), request);
                    throw new CustomException("验证码错误");
                }
            }
        }
        // 用户验证
        Authentication authentication = null;
        try {
            if (StrUtil.isNotBlank(loginBody.getPhoneCode())) {
                authentication = authenticationManager.authenticate(new EmailCodeAuthenticationToken(loginBody.getUsername(), loginBody.getPhoneCode()));
            } else {
                // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
                authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginBody.getUsername(), loginBody.getPassword()));
            }
        } catch (Exception e) {
            if (e instanceof BadCredentialsException) {
                asyncService.recordLogininfor(loginBody.getUsername(), Constants.LOGIN_FAIL, MessageUtils.message("user.password.not.match"), request);
                throw new CustomException("用户不存在/密码错误");
            } else if (e instanceof WrongEmailCodeException) {
                asyncService.recordLogininfor(loginBody.getUsername(), Constants.LOGIN_FAIL, e.getMessage(), request);
                throw e;
            } else {
                asyncService.recordLogininfor(loginBody.getUsername(), Constants.LOGIN_FAIL, e.getMessage(), request);
                throw new CustomException(e.getMessage());
            }
        }
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        if ((loginUser.getUser().getUserType().equals("00") && loginBody.getClient() != 0)
                || (loginUser.getUser().getUserType().equals("01") && loginBody.getClient() != 1)) {
            if (StrUtil.isNotBlank(loginBody.getPhoneCode())) {
                throw new CustomException("登录失败，账号或验证码错误");
            } else {
                throw new CustomException("登录失败，账号或密码错误");
            }
        }
        asyncService.recordLogininfor(loginBody.getUsername(), Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success"), request);
        recordLoginInfo(loginUser.getUser());
        // 生成token
        return tokenService.createToken(loginUser);
    }

    /**
     * 登录验证
     */
    public String loginByEmail(LoginBody loginBody) {
        HttpServletRequest request = ServletUtils.getRequest();

        // 用户验证
        Authentication authentication = null;
        try {
            if (StrUtil.isNotBlank(loginBody.getEmailVerificationCode())) {
                authentication = authenticationManager.authenticate(new EmailCodeAuthenticationToken(loginBody.getEmail(),loginBody.getEmailVerificationCode()));
            } else {
                // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
                authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginBody.getUsername(), loginBody.getPassword()));
            }
        } catch (Exception e) {
            if (e instanceof BadCredentialsException) {
                asyncService.recordLogininfor(loginBody.getUsername(), Constants.LOGIN_FAIL, MessageUtils.message("user.password.not.match"), request);
                throw new CustomException("用户不存在/密码错误");
            } else if (e instanceof WrongEmailCodeException) {
                asyncService.recordLogininfor(loginBody.getUsername(), Constants.LOGIN_FAIL, e.getMessage(), request);
                throw new CustomException(e.getMessage());
            } else {
                asyncService.recordLogininfor(loginBody.getUsername(), Constants.LOGIN_FAIL, e.getMessage(), request);
                throw new CustomException(e.getMessage());
            }
        }
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        if ((loginUser.getUser().getUserType().equals("00") && loginBody.getClient() != 0)
                || (loginUser.getUser().getUserType().equals("01") && loginBody.getClient() != 1)) {
            if (StrUtil.isNotBlank(loginBody.getPhoneCode())) {
                throw new CustomException("登录失败，账号或验证码错误");
            } else {
                throw new CustomException("登录失败，账号或密码错误");
            }
        }
        asyncService.recordLogininfor(loginBody.getUsername(), Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success"), request);
        recordLoginInfo(loginUser.getUser());
        // 生成token
        return tokenService.createToken(loginUser);
    }

    /**
     * 记录登录信息
     */
    public void recordLoginInfo(SysUser user) {
        user.setLoginIp(ServletUtils.getClientIP());
        user.setLoginDate(DateUtils.getNowDate());
        user.setUpdateBy(user.getUserName());
    }
}
