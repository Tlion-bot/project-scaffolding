package com.base.test.project.xxljob.service.impl;

import cn.hutool.core.date.DateUtil;
import com.base.test.project.xxljob.service.HelloService;
import org.springframework.stereotype.Service;

@Service
public class HelloServiceImpl implements HelloService {
    @Override
    public void methodA() {
        System.out.println("执行methodA"+ DateUtil.date());
    }

    @Override
    public void methodB() {
        System.out.println("执行methodA"+DateUtil.date());
    }
}
