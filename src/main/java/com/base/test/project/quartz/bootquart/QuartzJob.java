package com.base.test.project.quartz.bootquart;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;

@PersistJobDataAfterExecution //保持数据一致性

public class QuartzJob extends QuartzJobBean {
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

        try {
           // Thread.sleep(2000);
            System.out.println(context.getScheduler().getSchedulerInstanceId());
            System.out.println("taskname="+context.getJobDetail().getKey().getName());
            System.out.println("执行时间="+new Date());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
