package com.base.test.testClass;


import com.base.test.project.es.service.EsService;
import org.dromara.easyes.starter.register.EsMapperScan;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@EsMapperScan("com.base.test.project.es.mapper")
public class SpringDataESDocTest {
    //注入 ElasticsearchRestTemplate
    @Autowired
    private EsService esService;
    //创建索引并增加映射配置
    @Test
    public void createDoc(){
        esService.insertES(1);
    }


}
