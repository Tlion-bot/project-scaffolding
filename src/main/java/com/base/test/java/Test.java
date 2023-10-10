package com.base.test.java;

import cn.hutool.core.util.StrUtil;
import lombok.Data;

public class Test {


    public static void main(String[] args) {
        Cat cat=new Cat();
        cat.setAge(1);
        cat.setName("a");
        String a="baaabbcc";
        String a1="baa";

        String b=null;
        String c="";
        String[] d={"abbb","b"};
        String e="  av a ";
        // System.out.println(StrUtil.isBlank(a));
        // System.out.println(StrUtil.isBlank(b));
        // System.out.println(StrUtil.isBlankIfStr(a));
        // System.out.println(StrUtil.isBlankIfStr(b));
        // System.out.println(StrUtil.isBlankIfStr(c));
        // System.out.println(StrUtil.isBlankIfStr(cat));
        // System.out.println(cat);
        // System.out.println(StrUtil.isAllBlank(d));
        // System.out.println(StrUtil.trim(e));
        // System.out.println(StrUtil.startWith(a,a1));
        // System.out.println(StrUtil.startWith(a,a1,true));
        // System.out.println(StrUtil.startWithAny(a,d));
        // System.out.println(StrUtil.contains(a,a1));
        // System.out.println(StrUtil.containsBlank(a));

        System.out.println(StrUtil.getContainsStr(a,d));
    }
    @Data
    static class Cat{
        int age;
        String name;
    }
}
