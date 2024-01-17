package com.base.test.project.xxljob;

import cn.hutool.core.date.DateUtil;
import com.base.test.project.xxljob.service.HelloService;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SimpleJob {
    @Autowired
    private HelloService helloService;
    @XxlJob("demoHandler")
    public void demoJob() throws  Exception{
        helloService.methodA();
        System.out.println("执行定时任务"+ DateUtil.date());
    }
}
