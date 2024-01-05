package com.jinx.test.future;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureTest5 {
    public static void main(String[] args) {
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "Plan A";
        });
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "Plan B";
        });

        CompletableFuture<String> result = future1.applyToEither(future2, p -> p + " is selected");

        System.out.println(result.join());
    }
}
