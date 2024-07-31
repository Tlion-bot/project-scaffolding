package com.base.test.java.calculate;

public interface StrHelper {
    public static String getObjectValue(Object value) {
        return value != null ? value.toString() : "";
    }
}
