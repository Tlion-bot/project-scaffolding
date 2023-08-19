package com.base.test.common.contronller;

import com.base.test.common.core.domain.AjaxResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class ThreadPoolController {

    private final Logger logger = LoggerFactory.getLogger(ThreadPoolController.class);

    @Resource
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @GetMapping("/thread")
    public AjaxResult testThread() {
        threadPoolTaskExecutor.execute(() -> {
            try {
                Thread.sleep(5000);// 为了演示方便，让变成休眠10秒
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            logger.info("执行线程池任务");
            logger.info(Thread.currentThread().getName());//打印线程名称
        });// 需要传递Runnable对象

        logger.info("主线程名称:{}", Thread.currentThread().getName());//再打印主线程名称
        return AjaxResult.success("success");
    }
}
