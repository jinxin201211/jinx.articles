package com.jinx.test.future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;

public class CompletableFutureTest3 {
    public static void main(String[] args) {
        CompletableFuture<String> stringCompletableFuture = CompletableFuture.<String>supplyAsync(() -> {
                    System.out.println("[" + Thread.currentThread().getName() + "]" + "异步任务1");
                    try {
                        Thread.sleep(1000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return "1";
                })
                .thenApply(r -> {
//                    int i = 10 / 0;
                    System.out.println("[" + Thread.currentThread().getName() + "]" + "异步任务2");
                    try {
                        Thread.sleep(1000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return r + "2";
                })
                .handle((r, e) -> {
                    System.out.println("[" + Thread.currentThread().getName() + "]" + e.getMessage());
                    System.out.println("[" + Thread.currentThread().getName() + "]" + "异步任务3");
                    try {
                        Thread.sleep(1000L);
                    } catch (InterruptedException err) {
                        err.printStackTrace();
                    }
                    return r + "3";
                })
                .thenCombine(CompletableFuture.completedFuture("4"), (r1, r2) -> r1 + r2)
                .whenComplete((v, e) -> {
                    double num = ThreadLocalRandom.current().nextDouble();
                    System.out.println("[" + Thread.currentThread().getName() + "]" + num);
                    System.out.println("[" + Thread.currentThread().getName() + "]" + (num - 0.5));
                    System.out.println("[" + Thread.currentThread().getName() + "]" + (num - 0.5 > 0));
                    System.out.println("[" + Thread.currentThread().getName() + "]" + (num - 0.5 > 0 ? 0 : 1));
                    System.out.println("[" + Thread.currentThread().getName() + "]" + (1.0 / (num - 0.5 > 0 ? 0 : 1)));
//                    Integer _exception = 1 / (num - 0.5 > 0 ? 0 : 1);
                    System.out.println("[" + Thread.currentThread().getName() + "]" + "任务链结束，最终结果：" + v);
                })
                .exceptionally(err -> {
                    System.out.println("[" + Thread.currentThread().getName() + "]" + "执行任务出现异常：" + err.getMessage());
                    return "error";
                });

//        try {
//            Thread.sleep(2000L);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        System.out.println("[" + Thread.currentThread().getName() + "]" + stringCompletableFuture.join());
//        System.out.println(stringCompletableFuture.getNow("not yet"));
//        System.out.println(stringCompletableFuture.complete("interrupt") + "\t" + stringCompletableFuture.join());
    }
}
