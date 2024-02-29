package com.base.test.project.rabbitMQ.producer;

import com.base.test.common.config.rabbitMQ.ConfirmConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author frx
 * @version 1.0
 * @date 2022/7/26  19:17
 * desc:高级消息发布 消息生产者
 */
@Slf4j
@RequestMapping("/confirm")
@RestController
public class ProductController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    //开始发消息,测试确认
    @GetMapping("/sendMessage/{message}")
    public void sendMessage(@PathVariable("message") String message){
        //指定消息 id 为 1
        CorrelationData correlationData1 = new CorrelationData("1");
        rabbitTemplate.convertAndSend(ConfirmConfig.CONFIRM_EXCHANGE_NAME,
                ConfirmConfig.CONFIRM_ROUTING_KEY,message+"key1",correlationData1);
        log.info("发送消息内容:{}",message+"key1");

        //指定消息 id 为 2
        CorrelationData correlationData2 = new CorrelationData("2");
        String CONFIRM_ROUTING_KEY = "key2";
        rabbitTemplate.convertAndSend(ConfirmConfig.CONFIRM_EXCHANGE_NAME,
                CONFIRM_ROUTING_KEY,message+"key2",correlationData2);
        log.info("发送消息内容:{}",message+"key2");
    }

}
