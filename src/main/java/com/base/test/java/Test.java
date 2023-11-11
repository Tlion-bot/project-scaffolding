package com.base.test.java;

import lombok.Data;

import java.math.BigInteger;

public class Test {


    public static void main(String[] args) {

        int a=2^2;
        Long i= (long)Math.pow(2,67);
        BigInteger j= new BigInteger("9223372036854775808");
        System.out.println(i);
        System.out.println(j);
        System.out.println(a);

    }
    @Data
    static class Condition{
       private int id;
       private String condition_operator;
       private String condition_value;

        public Condition(int id, String condition_operator, String condition_value) {
            this.id = id;
            this.condition_operator = condition_operator;
            this.condition_value = condition_value;
        }
    }



}
