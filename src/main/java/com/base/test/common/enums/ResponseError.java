package com.base.test.common.enums;

/**
 * 返回状态
 * 
 * @author
 */
public enum ResponseError
{
    USER_ERROR(2000, "用户名或密码错误"),
    USER_EXIST(2001, "用户已经存在"),
    USER_NOT_EXIST(20021, "用户不存在"),
    CHANNEL_ERROR(20031, "渠道信息错误")
    ;

    private final int code;
    private final String info;

    ResponseError(int code, String info)
    {
        this.code = code;
        this.info = info;
    }

    public int getCode()
    {
        return code;
    }

    public String getInfo()
    {
        return info;
    }
}
