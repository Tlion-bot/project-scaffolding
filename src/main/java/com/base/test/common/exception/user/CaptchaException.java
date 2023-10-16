package com.base.test.common.exception.user;

import org.omg.CORBA.UserException;

/**
 * 验证码错误异常类
 *
 * @author baseWork
 */
public class CaptchaException extends UserException
{
    private static final long serialVersionUID = 1L;

    public CaptchaException()
    {
        super();
    }
}
