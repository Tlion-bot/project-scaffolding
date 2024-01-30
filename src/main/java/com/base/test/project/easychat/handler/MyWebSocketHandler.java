package com.base.test.project.easychat.handler;

import com.base.test.common.core.domain.entity.SysUser;
import com.base.test.project.system.service.impl.SysUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class MyWebSocketHandler {

    @Autowired
    private static SysUserServiceImpl userService;

    public static MyWebSocketHandler myWebSocketHandler;
    @PostConstruct
    public void init(){
        myWebSocketHandler = this;
    }

    // ... rest of the WebSocket logic
    public static SysUser getUser(int id){
       return userService.getById(id);
    }
}
