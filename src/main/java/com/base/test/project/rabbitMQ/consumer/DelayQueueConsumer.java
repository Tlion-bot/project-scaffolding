package com.base.test.project.rabbitMQ.consumer;

import com.base.test.common.config.rabbitMQ.DelayedQueueConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author frx
 * @version 1.0
 * @date 2022/7/26  1:00
 * desc:消费者，消费基于插件的延迟消息
 */
@Slf4j
@Component
public class DelayQueueConsumer {

    @RabbitListener(queues = DelayedQueueConfig.DELAYED_QUEUE_NAME)
    public void receiveDelayQueue(Message message){
        String msg = new String(message.getBody());
        log.info("当前时间：{},收到延时队列的消息：{}", new Date().toString(), msg);
    }
}
