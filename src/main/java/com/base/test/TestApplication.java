package com.base.test;

import com.base.test.common.config.FeignConfig;
import org.dromara.easyes.starter.register.EsMapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.test.context.ContextConfiguration;

// @SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@SpringBootApplication
@EnableScheduling
@EnableAsync
@ContextConfiguration(classes = {FeignConfig.class})
@EsMapperScan("com.base.test.project.es.mapper")
public class TestApplication {

    public static void main(String[] args)   {
        SpringApplication.run(TestApplication.class, args);
    }


}
