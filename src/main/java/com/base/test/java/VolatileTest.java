package com.base.test.java;

/**
 * 普通类：
 * 为了验证volatile的可见性
 */
public class VolatileTest {
    volatile  int number = 0;

    public void add(){
        this.number = 10;
    }


    public static void main(String[] args) {
        VolatileTest test1 = new VolatileTest();

        //创建第一个线程
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName()+"开始执行时，number = "+test1.number);

            try{ Thread.sleep(3000);}catch (Exception e){e.printStackTrace();}
            test1.add();//暂停3秒后，修改number的值。
            System.out.println(Thread.currentThread().getName()+"执行add()方法之后，number = "+test1.number);

        },"Thread_One").start();


        //第二个是main线程
        while (test1.number == 0){
            //如果第二个main线程 可以监测到number值的改变，就会跳出当前循环，执行后续程序。
        }

        System.out.println(Thread.currentThread().getName()+"程序结束！");

    }
}
