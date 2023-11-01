package com.base.test.common.enums;

public enum RedisKey {
    EMAIL_CODE("email");
    private final String code;




    RedisKey(String code) {
        this.code = code;
    }

    public String getCode()
    {
        return code;
    }

}
