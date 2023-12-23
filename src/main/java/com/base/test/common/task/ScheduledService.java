package com.base.test.common.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@Async("scheduledExecutorService")
public class ScheduledService {

    /**
     * fixedRate：定义一个按一定频率执行的定时任务
     * fixedDelay：定义该任务延迟执行时间。
     * cron：通过表达式来配置任务执行时间
     */
   // @Scheduled(cron = "0/5 * * * * *")
    public void scheduled(){
        log.info("线程" + Thread.currentThread().getId() + "开始执行");
        log.info("线程名称" + Thread.currentThread().getName() + "开始执行");

        try {
            TimeUnit.SECONDS.sleep(1);

        }catch (InterruptedException e){
            e.printStackTrace();
        }

        log.info("=====>>>>>使用cron  {}",System.currentTimeMillis());
    }

    // @Scheduled(fixedRate = 5000)
    // public void scheduled1() {
    //     log.info("=====>>>>>使用fixedRate{}", System.currentTimeMillis());
    // }
    //
    // @Scheduled(fixedDelay = 5000)
    // public void scheduled2() {
    //     log.info("=====>>>>>fixedDelay{}",System.currentTimeMillis());
    // }

}
