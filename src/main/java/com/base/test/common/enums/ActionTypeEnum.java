package com.base.test.common.enums;

public enum ActionTypeEnum {
    ADD(0),
    UPDATE(1);

    public Integer code;

    private ActionTypeEnum(Integer code) {
        this.code = code;
    }
}
