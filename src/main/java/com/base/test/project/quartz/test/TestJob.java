package com.base.test.project.quartz.test;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class TestJob {
    public static void main(String[] args) throws SchedulerException {
        int count=0;
        JobDetail jobDetail = JobBuilder.newJob(AbstractQuartzJob.class)
                .withIdentity("job1", "" + "group1")
                .usingJobData("job", "jobDetail")
                .usingJobData("name", "jobDetail")
                .usingJobData("count", count)
                .build();


        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "trigger1").
                startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(1)
                        .repeatForever())
                .usingJobData("trigger", "triggerDetail")
                .usingJobData("name", "triggerDetail")

                .build();


        try {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.scheduleJob(jobDetail, trigger);
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
