package com.base.test.framework.security.provider;

import org.springframework.security.core.AuthenticationException;

/**
 * @author nnc
 * @date 2023/11/1 9:33
 */
public class WrongPhoneCodeException extends AuthenticationException {
    public WrongPhoneCodeException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public WrongPhoneCodeException(String msg) {
        super(msg);
    }
}
