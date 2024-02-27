package com.base.test.java.sty;

import java.util.Date;
import java.util.LinkedList;

/**
 * 类名称:ProducerConsumerModel
 * 类描述: 用wait/notify 来实现生产者 消费者模型
 *
 * @author: https://javaweixin6.blog.csdn.net/
 * 创建时间:2020/8/29 18:59
 * Version 1.0
 */
public class ProducerConsumerModel {

    public static void main(String[] args) {
        EventStorage eventStorage = new EventStorage();
        Producer producer = new Producer(eventStorage);
        Consumer consumer = new Consumer(eventStorage);

        new Thread(producer).start();
        new Thread(consumer).start();
    }
}
/**
 * 生产者
 */
class Producer implements Runnable {
    private EventStorage storage;

    public Producer(EventStorage storage) {
        this.storage = storage;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            storage.put();
        }
    }
}

/**
 * 消费者
 */
class Consumer implements Runnable {
    private EventStorage storage;

    public Consumer(EventStorage storage) {
        this.storage = storage;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            storage.take();
        }
    }
}


class EventStorage {

    private int maxSize;
    //使用LinkedList的原因是有拿出集合中的第一个元素, 并且删除该元素的方法
    private LinkedList<Date> storage;

    public EventStorage() {
        //仓库的最大容量为10
        maxSize =10;
        storage = new LinkedList<>();
    }

    /**
     * put方法的作用是往队列中放东西,
     *         并且放了东西后, 要通知消费者,进行消费
     *         仓库满了之后, 不能再放入东西 ,并且进入wait状态
     *
     *  为了保证线程安全, 加上synchronized锁
     */
    public synchronized void put() {
        //首先判断仓库是否满了,如果满了,直接进入wait状态
        while (storage.size() == maxSize) {

            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //仓库没满, 生产产品, 并且, 唤醒消费者
        storage.add(new Date());
        System.out.println("仓库里有了 "+ storage.size()+" 个产品");
        notify();
    }

    /**
     * 消费者的take方法
     */
    public synchronized void take() {
        //首先判断容器中, 是否有元素, 如果为空,那么调用wait方法, 等待生产者生产线程
        while (storage.size() == 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("拿到了"+ storage.poll() + " , 现在仓库还剩下 "+storage.size());
        //唤醒生产者生产东西
        notify();
    }
}