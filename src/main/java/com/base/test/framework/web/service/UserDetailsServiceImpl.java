package com.base.test.framework.web.service;

import cn.hutool.core.lang.Validator;
import com.base.test.common.core.domain.entity.SysUser;
import com.base.test.common.core.domain.model.LoginUser;
import com.base.test.common.enums.UserStatus;
import com.base.test.common.exception.BaseException;
import com.base.test.project.system.service.ISysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 用户验证处理
 *
 * @author baseWork
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService
{
    private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    private ISysUserService userService;
    //
    // @Autowired
    // private SysPermissionService permissionService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        SysUser user = userService.selectUserByUserName(username);
        if (Validator.isNull(user))
        {
            log.info("登录用户：{} 不存在.", username);
            throw new UsernameNotFoundException("登录用户：" + username + " 不存在");
        }
        else if (UserStatus.DELETED.getCode().equals(user.getDelFlag()))
        {
            log.info("登录用户：{} 已被删除.", username);
            throw new BaseException("对不起，您的账号：" + username + " 已被删除");
        }
        else if (UserStatus.DISABLE.getCode().equals(user.getStatus()))
        {
            log.info("登录用户：{} 已被停用.", username);
            throw new BaseException("对不起，您的账号：" + username + " 已停用");
        }

        return createLoginUser(user);
    }


    public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException
    {
        SysUser user = userService.selectUserByEmail(email);
        if (Validator.isNull(user))
        {
            log.info("登录用户：{} 不存在.", email);
            throw new UsernameNotFoundException("登录用户：" + email + " 不存在");
        }
        else if (UserStatus.DELETED.getCode().equals(user.getDelFlag()))
        {
            log.info("登录用户：{} 已被删除.", email);
            throw new BaseException("对不起，您的账号：" + email + " 已被删除");
        }
        else if (UserStatus.DISABLE.getCode().equals(user.getStatus()))
        {
            log.info("登录用户：{} 已被停用.", email);
            throw new BaseException("对不起，您的账号：" + email + " 已停用");
        }

        return createLoginUser(user);
    }



    public UserDetails createLoginUser(SysUser user)
    {
        return new LoginUser(user);
    }
}
