package com.base.test.project.rabbitMQ.workQueues;

import com.base.test.project.rabbitMQ.utils.RabbitMQUtils;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author frx
 * @version 1.0
 * @date 2022/7/23  22:51
 * desc:这是一个工作线程，相当于之间的Consumer
 */
public class Work01 {

    //队列的名称
    public static final String QUEUE_NAME="hello";


    //接收消息
    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitMQUtils.getChannel();

        //消息的接受
        DeliverCallback deliverCallback = (consumerTag, message) ->{
            System.out.println("接收到的消息:"+new String(message.getBody()));
        };

        //消息接受被取消时，执行下面的内容
        CancelCallback cancelCallback = consumerTag -> {
            System.out.println(consumerTag+"消息被消费者取消消费接口回调逻辑");
        };

        //消息的接受
        channel.basicConsume(QUEUE_NAME,true,deliverCallback,cancelCallback);
    }
}
