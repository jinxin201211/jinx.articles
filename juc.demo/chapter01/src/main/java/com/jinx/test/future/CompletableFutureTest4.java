package com.jinx.test.future;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureTest4 {
    public static void main(String[] args) {
        CompletableFuture.<String>supplyAsync(() -> {
                    System.out.println("[" + Thread.currentThread().getName() + "]" + "异步任务1");
                    try {
                        Thread.sleep(1000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return "1";
                })
                .thenApply(r -> {
                    System.out.println("[" + Thread.currentThread().getName() + "]" + "异步任务2");
                    try {
                        Thread.sleep(1000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return r + "2";
                })
                .thenAccept(r -> {
                    System.out.println("[" + Thread.currentThread().getName() + "]" + r);
                });
        try {
            Thread.sleep(3000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(CompletableFuture.supplyAsync(() -> "resultA").thenRun(() -> {
        }).join());
        System.out.println(CompletableFuture.supplyAsync(() -> "resultA").thenAccept(System.out::println).join());
        System.out.println(CompletableFuture.supplyAsync(() -> "resultA").thenApply(r -> r + "resultB").join());
    }
}
