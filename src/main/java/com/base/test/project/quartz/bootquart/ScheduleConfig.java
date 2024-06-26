//
// package com.base.test.project.quartz.bootquart;
//
// import org.quartz.Scheduler;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.beans.factory.config.PropertiesFactoryBean;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.core.io.ClassPathResource;
// import org.springframework.scheduling.annotation.EnableScheduling;
// import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
// import org.springframework.scheduling.quartz.SchedulerFactoryBean;
//
// import javax.sql.DataSource;
// import java.io.IOException;
// import java.util.Properties;
// import java.util.concurrent.Executor;
//
// @Configuration
// @EnableScheduling
// public class ScheduleConfig {
//     @Autowired
//     private DataSource dataSource;
//
//     @Bean
//     public Scheduler scheduler() throws IOException {
//         return schedulerFactoryBean().getScheduler();
//     }
//
//     @Bean
//     public SchedulerFactoryBean schedulerFactoryBean() throws IOException {
//         SchedulerFactoryBean factory = new SchedulerFactoryBean();
//         factory.setSchedulerName("cluster_schedule");
//         factory.setDataSource(dataSource);
//         factory.setApplicationContextSchedulerContextKey("application");
//         factory.setQuartzProperties(quartzProperties());
//         factory.setTaskExecutor(schedulerThreadPool());
//         factory.setStartupDelay(10);
//         return factory;
//     }
//
//     @Bean
//     public Properties quartzProperties() throws IOException {
//         PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
//         propertiesFactoryBean.setLocation(new ClassPathResource("/application-dev.properties"));
//         propertiesFactoryBean.afterPropertiesSet();
//         return propertiesFactoryBean.getObject();
//     }
//
//     @Bean
//     public Executor schedulerThreadPool() {
//         ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//         executor.setCorePoolSize(Runtime.getRuntime().availableProcessors());
//         executor.setMaxPoolSize(Runtime.getRuntime().availableProcessors());
//         executor.setQueueCapacity(Runtime.getRuntime().availableProcessors());
//         return executor;
//     }
// }
//
