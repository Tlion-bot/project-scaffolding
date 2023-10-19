package com.base.test.java;

import lombok.Data;

public class Test {


    public static void main(String[] args) {
       int x=5;int num,num1;
        num=++x*--x;
        num1=x++*x--;
        System.out.println(num);
        System.out.println(num1);
    }
    @Data
    static class Cat{
        int age;
        String name;
    }
}
