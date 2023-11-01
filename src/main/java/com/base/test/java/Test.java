package com.base.test.java;

import lombok.Data;

public class Test {


    public static void main(String[] args) {
        Condition condition=new Condition(1,">","60");
        Condition condition1=new Condition(2,"<","30");
        Condition condition3=new Condition(3,">","0");


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
