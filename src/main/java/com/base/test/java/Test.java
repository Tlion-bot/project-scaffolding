package com.base.test.java;

import cn.hutool.core.util.StrUtil;

import java.math.BigInteger;

public class Test {
    public static void main(String[] args) {
        String config=null;

        System.out.println((StrUtil.isNotBlank(config) ? new BigInteger(config) : BigInteger.valueOf(0)));
    }

}
