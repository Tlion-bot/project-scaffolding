package com.base.test.java.reflex;

import lombok.Data;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 当我们从类中获取了一个方法后，我们就可以用 invoke() 方法来调用这个方法。invoke 方法的原型为:
 * @author nnc
 * @date 2023/11/13 9:37
 */
public class Invoke {
    public static void main(String[] args) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Class<?> klass = methodClass1.class;
        //创建methodClass的实例
        Object obj = klass.newInstance();
        //获取methodClass类的add方法
        Method method = klass.getMethod("add",int.class,int.class);
        System.out.println(klass.getConstructor());
        //调用method对应的方法 => add(1,4)
        Object result = method.invoke(klass.getConstructor().newInstance(),1,4);
        System.out.println(result);
    }
}
@Data
class methodClass1 {
    public final int fuck = 3;
    public int add(int a,int b) {
        return a+b;
    }
    public int sub(int a,int b) {
        return a+b;
    }
}