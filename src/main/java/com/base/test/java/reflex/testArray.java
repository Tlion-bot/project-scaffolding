package com.base.test.java.reflex;

import java.lang.reflect.Array;

public class testArray {
    public static void testArray() throws ClassNotFoundException {
        Class<?> cls = Class.forName("java.lang.String");
       String [] A = new String[0];
        System.out.println(A.getClass());
        Object array = Array.newInstance(cls,25);
        //往数组里添加内容
        Array.set(array,0,"hello");
        Array.set(array,1,"Java");
        Array.set(array,2,"fuck");
        Array.set(array,3,"Scala");
        Array.set(array,4,"Clojure");
        //获取某一项的内容
        System.out.println(Array.get(array,3));
    }

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        testArray();
    }
}
