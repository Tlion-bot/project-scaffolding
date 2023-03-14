package com.base.test.contronller;

import com.base.test.domain.Logintest;
import com.base.test.service.impl.LogintestServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("test")
public class testcontronller {
    @Autowired
    LogintestServiceImpl logintestService;

    @GetMapping("hello")
    public List<Logintest> hello(){
//        Gson gson = new Gson();
//        System.out.println(gson.toJson(logintestService.selectAll()));
//        System.out.println(logintestService.selectAll());
        Long a= 1L;
        Logintest logintest=new Logintest();


        return logintestService.selectAll();

    }
}
