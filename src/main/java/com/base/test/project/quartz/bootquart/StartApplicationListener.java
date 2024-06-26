/*
package com.base.test.project.quartz.bootquart;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class StartApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
   private Scheduler scheduler;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        TriggerKey triggerKey = TriggerKey.triggerKey("trigger4", "group4");

        try {
            Trigger trigger = scheduler.getTrigger(triggerKey);
            if (trigger == null) {
                trigger = TriggerBuilder.newTrigger()
                        .withIdentity(triggerKey)

                        // .withSchedule(CronScheduleBuilder.cronSchedule())
                        .build();
                JobDetail jobDetail = JobBuilder
                        .newJob(QuartzJob.class)
                        .withIdentity("job4","group4")
                        .build();




                scheduler.scheduleJob(jobDetail,trigger);
               scheduler.start();
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
*/
