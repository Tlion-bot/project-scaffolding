package com.base.test.common.contronller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("test")
@RestController
public class RedisController {
    @PostMapping("get")
    public String redisGet(@RequestBody String str){
        String rest = (String) redisTemplate.opsForValue().get(str);
        return  rest;
    }
    @Autowired
    private RedisTemplate redisTemplate;
    @PostMapping("set")
    public String redisTest(@RequestBody String[] str){
        redisTemplate.opsForValue().set(str[0],str[1]);
        return "已存入";
    }
}