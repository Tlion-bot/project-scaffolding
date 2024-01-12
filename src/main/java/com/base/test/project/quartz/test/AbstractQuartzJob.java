package com.base.test.project.quartz.test;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * 抽象quartz调用
 *
 * @author fei.yin
 */
@DisallowConcurrentExecution //不并发执行
@PersistJobDataAfterExecution
public class AbstractQuartzJob implements Job {
    public void setName(String name) {
        this.name = name;
    }

    private String name;
    private static final Logger log = LoggerFactory.getLogger(AbstractQuartzJob.class);

    /**
     * 线程本地变量
     */
    private static ThreadLocal<Date> threadLocal = new ThreadLocal<>();

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
/*
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        JobDataMap triggerMap = context.getTrigger().getJobDataMap();
        JobDataMap mergedJobDataMap = context.getMergedJobDataMap();
        System.out.println("jobDataMap:"+jobDataMap.getString("job"));
        System.out.println("triggerMap:"+triggerMap.getString("trigger"));
        System.out.println("mergedJobDataMap:"+mergedJobDataMap.getString("trigger"));
        System.out.println("name:"+name);
*/
/*
        System.out.println("jobDetail:"+System.identityHashCode(context.getJobDetail()));
        System.out.println("job:"+System.identityHashCode(context.getJobInstance()));
*/
/*        System.out.println("execute:"+new Date());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
*/
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        jobDataMap.put("count",jobDataMap.getInt("count")+1);

        System.out.println("triggerMap count:"+jobDataMap.getInt("count"));
    }


}
