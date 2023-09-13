package com.base.test.project.business.test;

import com.base.test.project.business.domain.AutoWiredBean;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
class Springboot02WebApplicationTests {


    private AutoWiredBean autoWiredBean;

    @Autowired
    public Springboot02WebApplicationTests (AutoWiredBean autoWiredBean){
        this.autoWiredBean = autoWiredBean;
    }

    @Test
    void contextLoads() {
        System.out.println(autoWiredBean);
        System.out.println(autoWiredBean.getId());  //0
        System.out.println(autoWiredBean.getName());    //null

    }

}
