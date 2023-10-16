package com.base.test.framework.security.provider;

import org.springframework.security.core.AuthenticationException;

/**
 * @Author lzs
 * @Date 2022/5/5 17:15
 **/
public class WrongPhoneCodeException extends AuthenticationException {
    public WrongPhoneCodeException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public WrongPhoneCodeException(String msg) {
        super(msg);
    }
}
