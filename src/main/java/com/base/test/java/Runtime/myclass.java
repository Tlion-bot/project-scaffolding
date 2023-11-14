package com.base.test.java.Runtime;

import java.io.IOException;

/**
 * Runtime类的exec()方法返回一个Process对象，表示操作系统的一个进程，通过该对象可以对产生的新进程进行管理。关闭进程只需要调用destroy（）方法即可。
 * 实现打开的记事本在秒后自动关闭的功能：
 * Unhandled exception type IOException：未处理的异常类型IOException
 * @author nnc
 * @date 2023/11/13 17:17
 */
public class myclass {
    public static void main(String[] args) throws IOException, InterruptedException {
        Runtime rt = Runtime.getRuntime(); // 获取
        System.out.println("处理器的个数: " + rt.availableProcessors()+"个");
        System.out.println("空闲内存数量: " + rt.freeMemory() / 1024 / 1024
                + "M");
        System.out.println("最大可用内存数量: " + rt.maxMemory() / 1024 /
                1024 + "M");
        System.out.println("虚拟机中内存总量: " + rt.totalMemory() / 1024 /
                1024 + "M");
        //rt.exec("Xshell.exe");
        System.out.println(rt.exec("cmd dir/W"));
        // try {
        //     Process process = Runtime.getRuntime().exec("notepad.exe");
        //
        //     // 休眠3秒
        //     Thread.sleep(3000);
        //
        //     // 执行任务管理器中终止记事本进程的命令
        //     Runtime.getRuntime().exec("taskkill /F /IM notepad.exe");
        // } catch (IOException e) {
        //     e.printStackTrace();
        // } catch (InterruptedException e) {
        //     e.printStackTrace();
        // }
        Process process = rt.exec("Notepad.exe");//调用exec方法得到表示进程的对象
        Thread.sleep(3000);//进程休眠3秒
        process.destroy();//杀掉进程


    }
}