package com.base.test.common.core.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 用户登录对象
 *
 * @author baseWork
 */

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class LoginBody
{
    /**
     * 用户名
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 手机验证码
     */
    private String phoneCode;

    /**
     * 验证码
     */
    private String code;

    /**
     * 唯一标识
     */
    private String uuid = "";

    /**
     * 登录客户端：0-管理后台,1-web,2-App
     */
    private Integer client;
    /**
     * 邮箱验证码
     */
    private String emailVerificationCode;
    /**
     * 邮箱
     */
    private String email;

}
