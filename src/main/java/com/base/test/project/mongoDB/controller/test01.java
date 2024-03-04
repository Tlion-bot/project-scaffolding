package com.base.test.project.mongoDB.controller;

import com.base.test.common.core.domain.AjaxResult;
import com.base.test.project.mongoDB.domain.Students;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mongDB")
public class test01 {
    @Autowired
    private  MongoTemplate mongoTemplate;

    @PostMapping("/add")
    public AjaxResult add()
    {
        Students students=new Students();
        students.setAge("12");
        students.setName("李斯");
        students.setGender("男");
        mongoTemplate.save(students);
        return AjaxResult.success();
    }


}
