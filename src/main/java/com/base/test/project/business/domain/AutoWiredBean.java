package com.base.test.project.business.domain;

import lombok.Data;
import org.springframework.stereotype.Service;

@Data
@Service
public class AutoWiredBean {
    private int id;
    private String name;

    public AutoWiredBean(){
        System.out.println("无参构造函数");
    }

    public AutoWiredBean(int id, String name) {
        this.id = id;
        this.name = name;
        System.out.println("有参构造函数");
    }
}
