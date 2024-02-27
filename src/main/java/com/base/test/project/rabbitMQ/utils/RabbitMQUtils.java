package com.base.test.project.rabbitMQ.utils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author frx
 * @version 1.0
 * @date 2022/7/23  22:46
 * desc:此类为连接工厂创建信道的工具类
 */
public class RabbitMQUtils {

    public static Channel getChannel() throws IOException, TimeoutException {

        //创建一个连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        //工厂IP 连接RabbitMQ对列
        factory.setHost("192.168.70.128");
        //用户名
        factory.setUsername("admin");
        //密码
        factory.setPassword("1982570424");

        //创建连接
        Connection connection = factory.newConnection();
        //获取信道
        Channel channel = connection.createChannel();

        return channel;
    }
}
