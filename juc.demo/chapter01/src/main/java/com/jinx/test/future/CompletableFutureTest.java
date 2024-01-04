package com.jinx.test.future;

import java.util.concurrent.*;

public class CompletableFutureTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ThreadPoolExecutor tpool = new ThreadPoolExecutor(3, 5, 2000L, TimeUnit.SECONDS, new ArrayBlockingQueue<>(200));
        CompletableFuture<?> future1 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2233L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "任务1已完成";
        }, tpool);
        CompletableFuture<?> future2 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3344L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "任务2已完成";
        }, tpool);
        CompletableFuture<?> future3 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(8899L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "任务3已完成";
        }, tpool);

        CompletableFuture<Void> mixedFuture = CompletableFuture.allOf(future1, future2, future3);
        new Thread(() -> {
            while (true) {
                if (mixedFuture.isDone()) {
                    System.out.println("所有任务都完成");
                    break;
                }
            }
        }).start();
        new Thread(() -> {
            while (true) {
                if (future1.isDone()) {
                    try {
                        System.out.println(future1.get());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }).start();
        new Thread(() -> {
            while (true) {
                if (future2.isDone()) {
                    try {
                        System.out.println(future2.get());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }).start();
        new Thread(() -> {
            while (true) {
                if (future3.isDone()) {
                    try {
                        System.out.println(future3.get());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }).start();
    }
}
