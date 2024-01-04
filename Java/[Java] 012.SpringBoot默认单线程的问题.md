# $$SpringBoot默认单线程的问题$$

SpringBoot默认是单线程的，在执行多个定时任务时，如果其中一个任务执行时间过长，可能会导致其他任务被阻塞。

```java
    @Scheduled(cron = "* */1 * * * ?")
    public void task1() {
        log.info("TASK 1 OUTPUT");
    }

    @Scheduled(cron = "* */1 * * * ?")
    public void task2() {
        log.info("任务 2 OUTPUT");
        try {
            Thread.sleep(10000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
```

上述代码中，task2和task1在同一个线程中执行，task2任务会阻塞task1任务的执行。

![001](/imgs/java012/001.jpg)

方法一：添加 `@Async` 注解

 `@Async` 注解是Spring框架中的一个注解，用于标记一个方法是异步方法。这意味着当该方法被调用时，它将在一个单独的线程中执行，而不是在当前线程中执行。注意 `@Async` 需要配合 `@EnableAsync` 来使用。

```java
    @Scheduled(cron = "* */1 * * * ?")
    @Async
    public void task1() {
        log.info("TASK 1 OUTPUT");
    }

    @Scheduled(cron = "* */1 * * * ?")
    @Async
    public void task2() {
        log.info("任务 2 OUTPUT");
        try {
            Thread.sleep(10000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
```

![002](/imgs/java012/002.jpg)

可以看出，定时任务每次执行都使用线程池中的空闲线程，不会阻塞其他任务的执行。即使时同一个任务，也不会出现阻塞的情况。

 `@Async` 默认使用Spring提供的线程池来执行异步任务，我们也可以指定自己的线程池。

 ```java
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
    @Async("threadPoolTaskExecutor")
    public void task1() {
        log.info("TASK 1 OUTPUT");
    }

    @Scheduled(cron = "* */1 * * * ?")
    @Async("threadPoolTaskExecutor")
    public void task2() {
        log.info("任务 2 OUTPUT");
        try {
            Thread.sleep(10000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
```

![004](/imgs/java012/004.jpg)

方法二：向Spring容器中注入一个 `ThreadPoolTaskScheduler` 的bean

Spring会用这个bean来调度定时任务

```java
    @Bean
    public ThreadPoolTaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(10);
        scheduler.setThreadNamePrefix("MyScheduler-");
        scheduler.initialize();
        return scheduler;
    }
```

![003](/imgs/java012/003.jpg)

此时，task2不再阻塞task1，但是task2自身还是会被阻塞。
