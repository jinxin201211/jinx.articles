package com.jinx.test.future;

import java.util.Random;
import java.util.concurrent.*;

public class CompletableFutureTest2 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ThreadPoolExecutor tpool = new ThreadPoolExecutor(3, 5, 2000L, TimeUnit.SECONDS, new ArrayBlockingQueue<>(200));
        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
                    System.out.println("[" + Thread.currentThread().getName() + "]" + "开始执行任务1...");

                    try {
                        Thread.sleep(999L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    int num = new Random().nextInt(10000);

                    if (num < 5000) {
                        double error = 1 / 0; //抛异常
                    }

                    System.out.println("[" + Thread.currentThread().getName() + "]" + "任务1完成");
                    return num;
//                }) //默认线程池ForkJoinPool
                }, tpool) //自定义线程池
                .whenComplete((v, e) -> {
                    System.out.println("[" + Thread.currentThread().getName() + "]" + "任务1完成后的回调");
                    System.out.println("[" + Thread.currentThread().getName() + "]" + "任务1的返回值：" + v);
                })
                .exceptionally(e -> {
                    e.printStackTrace();
                    System.out.println("[" + Thread.currentThread().getName() + "]" + "出现异常了");
                    System.out.println("[" + Thread.currentThread().getName() + "]" + "异常原因" + e.getCause());
                    System.out.println("[" + Thread.currentThread().getName() + "]" + "异常信息" + e.getMessage());
                    return 0;
                });

        System.out.println("[" + Thread.currentThread().getName() + "]" + "主线程正在执行其他任务...");
//        System.out.println("[" + Thread.currentThread().getName() + "]" + completableFuture.get());
        System.out.println("[" + Thread.currentThread().getName() + "]" + completableFuture.join());

        tpool.shutdown();
    }
}
