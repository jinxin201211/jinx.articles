package com.jinx.test.future;

import java.util.concurrent.*;

public class FutureTaskTest {
    public static void main(String[] args) throws Exception {
        FutureTask<String> futureTask = new FutureTask((Callable<String>) () -> {
            Thread.sleep(3000);
            return "返回任务结果";
        });

        ThreadPoolExecutor tpool = new ThreadPoolExecutor(3, 5, 2000L, TimeUnit.SECONDS, new ArrayBlockingQueue<>(200));
        Future<?> submit = tpool.submit(futureTask);

        while (true) {
            if (futureTask.isDone()) {
                System.out.println("submit get: " + submit.get());
                System.out.println("futuretask get: " + futureTask.get());
                System.out.println("------------------------------------");
                Thread.sleep(100L);
                break;
            }
        }
    }
}
