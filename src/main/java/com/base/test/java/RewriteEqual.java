package com.base.test.java;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Objects;

/**
 * 重写equals方法必须重写hashcode方法，@Data注解同时重写了equal方法和hashcode方法
 * @author nnc
 * @date 2023/9/1 16:14
 */
public class RewriteEqual {
    public static void main(String[] args) {
        String a1=new String("aa");
        String b1=new String("aa");
        String a=new String("abc");
        String b=new String("abc");


        Student student1 = new Student("张三", 20, "男");
        Student student2 = new Student("张三", 20, "男");
        boolean equals = Objects.equals(student1, student2);
        boolean equal1=student1.equals(student2);
        boolean equal2=a1.equals(b1);
        System.out.println("student1" + student1.toString());
        System.out.println("student2" + student2.toString());
        System.out.println(equals);
         System.out.println(equal1);
        System.out.println(equal2);
    }
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor

    static class Student {
        private String name;

        private Integer age;

        private String sex;
    }
}
