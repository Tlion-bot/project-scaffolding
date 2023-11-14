package com.base.test.java.reflex;

public class reflex {
    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        Class<?> klass = int.class;
        Class<?> classInt = Integer.TYPE;
        System.out.println(klass);
        System.out.println(classInt);
/**
 *从JVM的角度看，我们使用关键字new创建一个类的时候，这个类可以没有被加载。但是使用newInstance()方法的时候，就必须保证：
 *1、这个类已经加载；2、这个类已经连接了。而完成上面两个步骤的正是Class的静态方法forName()所完成的，这个静态方法调用了启动类加载器， 即加载java API的那个加载器。
 */
        A a= (A) Class.forName("com.base.test.java.reflex.A").newInstance();
        System.out.println(a);

    }


}
