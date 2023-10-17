package com.base.test.common.core.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.Size;

/**
 * @author nnc
 * @date 2023/3/22 16:28
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class RegisterBody
{
    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户密码
     */
    private String password;
    /**
     * 用户昵称
     */
    private String nickName;
    @Size(min = 0, max = 11, message = "手机号码长度不能超过11个字符")
    private String phonenumber;
    /**
     * 验证码
     */
    private String code;

    /**
     * 唯一标识
     */
    private String uuid = "";
}