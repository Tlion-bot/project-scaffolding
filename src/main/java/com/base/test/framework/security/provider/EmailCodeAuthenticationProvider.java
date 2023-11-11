package com.base.test.framework.security.provider;

import com.base.test.common.core.domain.model.LoginUser;
import com.base.test.common.core.redis.RedisCache;
import com.base.test.common.enums.RedisKey;
import com.base.test.framework.web.service.UserDetailsServiceImpl;
import com.base.test.project.system.service.ISysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;


/**
 * 手机验证码认证
 *
 * @Author lzs
 * @Date 2022/4/29 10:49
 **/
@Slf4j
public class EmailCodeAuthenticationProvider implements AuthenticationProvider, InitializingBean {

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;
    private ISysUserService userService;

    private UserDetailsChecker preAuthenticationChecks = new AccountStatusUserDetailsChecker();
    private GrantedAuthoritiesMapper authoritiesMapper = new SimpleAuthorityMapper();
    private MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
    private RedisCache redisCache;
    private PasswordEncoder passwordEncoder;
    private volatile String userNotFoundEncodedPassword;
    private static final String USER_NOT_FOUND_PASSWORD = "userNotFoundPassword";
    private boolean forcePrincipalAsString = false;

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(this.userService, "A ISysUserService must be set");
        Assert.notNull(this.userDetailsServiceImpl, "A UserDetailsService must be set");
        Assert.notNull(this.redisCache, "A RedisCache must be set");
        Assert.notNull(this.passwordEncoder, "A PasswordEncoder must be set");
    }

    /**
     * 不使用userCache
     *
     * @param authentication
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        //获取用户名
        String username = determineUsername(authentication);
        //获取用户
        UserDetails user = retrieveUser(username, (EmailCodeAuthenticationToken) authentication);
        //检查用户的状态
        preAuthenticationChecks.check(user);
        //校验手机验证码
        additionalAuthenticationChecks(user, (EmailCodeAuthenticationToken) authentication);
        //此处不需要
        // postAuthenticationChecks.check

        Object principalToReturn = user;

        if (forcePrincipalAsString) {
            principalToReturn = user.getUsername();
        }
        return createSuccessAuthentication(principalToReturn, authentication, user);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return EmailCodeAuthenticationToken.class.isAssignableFrom(authentication);
    }

    private String determineUsername(Authentication authentication) {
        return (authentication.getPrincipal() == null) ? "NONE_PROVIDED" : authentication.getName();
    }

    /**
     * 需要处理计时攻击吗？
     * 计时攻击仍然用户处理密码的那一套，不清楚有没有问题
     *
     * @param username
     * @param authentication
     * @return
     * @throws AuthenticationException
     */
    protected UserDetails retrieveUser(String username, EmailCodeAuthenticationToken authentication)
            throws AuthenticationException {
        prepareTimingAttackProtection();
        try {
            UserDetails loadedUser = userDetailsServiceImpl.loadUserByEmail(username);
            if (loadedUser == null) {
                throw new InternalAuthenticationServiceException(
                        "UserDetailsService returned null, which is an interface contract violation");
            }
            return loadedUser;
        } catch (UsernameNotFoundException ex) {
            mitigateAgainstTimingAttack(authentication);
            throw ex;
        } catch (InternalAuthenticationServiceException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex);
        }
    }

    protected void additionalAuthenticationChecks(UserDetails userDetails,
                                                  EmailCodeAuthenticationToken authentication) throws AuthenticationException {
        if (authentication.getCredentials() == null) {
            log.debug("Failed to authenticate since no credentials provided");
            throw new WrongEmailCodeException(this.messages
                    .getMessage("EmailCodeAuthenticationProvider.badCredentials", "验证码错误"));
        }
        if (!(userDetails instanceof LoginUser)) {
            throw new WrongEmailCodeException(this.messages
                    .getMessage("EmailCodeAuthenticationProvider.badCredentials", "验证码错误"));
        }
        String email = ((LoginUser)userDetails).getUser().getEmail();
        String emailCode = authentication.getCredentials().toString();
        String emailCodeCode = redisCache.getCacheObject(RedisKey.EMAIL_CODE.getCode() + email);
        if (!StringUtils.hasText(emailCodeCode)) {
            throw new WrongEmailCodeException(this.messages
                    .getMessage("EmailCodeAuthenticationProvider.badCredentials", "请点击发送验证码"));
        }
        if (!emailCode.equals(emailCodeCode)) {
            log.debug("Failed to authenticate since phone code does not match stored value");
            //处理暴力破解
            // String checkTimes = redisCache.getCacheObject(PHONE_CODE_CHECK_TIMES_PREFIX + phone);
            // if (StringUtils.hasText(checkTimes)) {
            //     if (PHONE_CODE_TOTAL_CHECK_TIMES >= Integer.parseInt(checkTimes)) {
            //         redisCache.deleteObject(PHONE_CODE_KEY + phone);
            //         redisCache.deleteObject(PHONE_CODE_CHECK_TIMES_PREFIX + phone);
            //         //修改账号状态
            //         userService.updateUserStatusByPhone(phone, UserStatus.DISABLE.getCode());
            //         throw new WrongEmailCodeException(this.messages
            //                 .getMessage("EmailCodeAuthenticationProvider.accountLocked", "验证码错误次数过多，账户已锁定"));
            //     } else {
            //         redisCache.increment(PHONE_CODE_CHECK_TIMES_PREFIX + phone);
            //     }
            // } else {
            //     redisCache.setCacheObject(PHONE_CODE_CHECK_TIMES_PREFIX + phone, "1", PHONE_CODE_KEY_EXPIRATION, TimeUnit.MINUTES);
            // }
            throw new WrongEmailCodeException(this.messages
                    .getMessage("EmailCodeAuthenticationProvider.badCredentials", "验证码错误"));
        }
        redisCache.deleteObject(RedisKey.EMAIL_CODE.getCode() + email);
        // redisCache.deleteObject(PHONE_CODE_CHECK_TIMES_PREFIX + phone);
    }

    protected Authentication createSuccessAuthentication(Object principal, Authentication authentication,
                                                         UserDetails user) {
        // Ensure we return the original credentials the user supplied,
        // so subsequent attempts are successful even with encoded passwords.
        // Also ensure we return the original getDetails(), so that future
        // authentication events after cache expiry contain the details
        EmailCodeAuthenticationToken result = new EmailCodeAuthenticationToken(principal, authentication.getCredentials());
        result.setDetails(authentication.getDetails());
        log.debug("Authenticated user");
        return result;
    }

    private void prepareTimingAttackProtection() {
        if (this.userNotFoundEncodedPassword == null) {
            this.userNotFoundEncodedPassword = this.passwordEncoder.encode(USER_NOT_FOUND_PASSWORD);
        }
    }

    private void mitigateAgainstTimingAttack(EmailCodeAuthenticationToken authentication) {
        if (authentication.getCredentials() != null) {
            String presentedPassword = authentication.getCredentials().toString();
            this.passwordEncoder.matches(presentedPassword, this.userNotFoundEncodedPassword);
        }
    }

    public ISysUserService getUserService() {
        return userService;
    }

    public void setUserService(ISysUserService userService) {
        this.userService = userService;
    }

    public UserDetailsService getUserDetailsService() {
        return userDetailsServiceImpl;
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsServiceImpl = userDetailsServiceImpl;
    }

    public RedisCache getRedisCache() {
        return redisCache;
    }

    public void setRedisCache(RedisCache redisCache) {
        this.redisCache = redisCache;
    }

    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }

    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public void setForcePrincipalAsString(boolean forcePrincipalAsString) {
        this.forcePrincipalAsString = forcePrincipalAsString;
    }
}
