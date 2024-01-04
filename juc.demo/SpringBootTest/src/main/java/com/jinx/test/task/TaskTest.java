package com.jinx.test.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

@Component
public class TaskTest {

    private static final Logger log = LoggerFactory.getLogger(TaskTest.class);

    @Bean
    public ThreadPoolTaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(10);
        scheduler.setThreadNamePrefix("MyScheduler-");
        scheduler.initialize();
        return scheduler;
    }

    @Bean("threadPoolTaskExecutor")
    public ThreadPoolTaskExecutor getThreadPoolTaskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(10);
        taskExecutor.setMaxPoolSize(20);
        taskExecutor.setQueueCapacity(100);
        taskExecutor.initialize();
        taskExecutor.setThreadNamePrefix("MySchedulerX-");
        return taskExecutor;
    }

    @Scheduled(cron = "* */1 * * * ?")
//    @Async("threadPoolTaskExecutor")
    public void task1() {
        new Thread(() -> {
            log.info("TASK 1 OUTPUT");
        }).start();
    }

    @Scheduled(cron = "* */1 * * * ?")
//    @Async("threadPoolTaskExecutor")
    public void task2() {
        new Thread(() -> {
            log.info("任务 2 OUTPUT");
            try {
                Thread.sleep(10000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
