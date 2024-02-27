package com.base.test.java.sty;

/**
 *finalize()方法调用不是及时执行
 * @author nnc
 * @date 2023/9/1 16:18
 */
public class TestFinalize {

    @Override
    protected void finalize() throws Throwable {
        System.out.println("finalize()方法执行了");
    }

    public static void main(String[] args) {
        TestFinalize tf = new TestFinalize();
        tf = null;//令tf为null，让GC对其进行回收
        System.gc();//程序员手动请求gc
        //在java中finalize()并不会每次都及时执行，因为GC的自动回收机制，导致垃圾回收的时机具有不确定性，jvm会在适当的时机对垃圾对象进行处理，
        // 因此，是无法保证finalize()方法 被及时执行的，甚至无法保证会被执行，程序可能始终无法触发垃圾回收
    }

}
//输出finalize()方法执行了
