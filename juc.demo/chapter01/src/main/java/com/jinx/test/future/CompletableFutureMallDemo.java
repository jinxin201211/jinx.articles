package com.jinx.test.future;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class CompletableFutureMallDemo {
    static List<Mall> malls = Arrays.asList(new Mall("京东"), new Mall("淘宝"), new Mall("拼多多"), new Mall("天猫"), new Mall("抖音"));

    private static List<String> getPrices1(List<Mall> malls, String product) {
        return malls
                .stream()
                .map(p -> String.format("在%s购物，商品%s的价格是：%.2f",
                        p.getName(),
                        product,
                        p.getPrice(product))
                )
                .collect(Collectors.toList());
    }

    private static List<String> getPrices2(List<Mall> malls, String product) {
        return malls
                .stream()
                .map(p ->
                        CompletableFuture.supplyAsync(() ->
                                String.format("在%s购物，商品%s的价格是：%.2f",
                                        p.getName(),
                                        product,
                                        p.getPrice(product))
                        )
                )
                .collect(Collectors.toList())
                .stream()
                .map(p -> p.join())//join是阻塞的，所以要先让任务全部完成再来join
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        String product = "iPhoneX";
        getPrices1(malls, product).forEach(System.out::println);
        long time2 = System.currentTimeMillis();
        System.out.println(time2 - time1);
        time1 = time2;
        getPrices2(malls, product).forEach(System.out::println);
        time2 = System.currentTimeMillis();
        System.out.println(time2 - time1);
    }
}

class Mall {
    @Getter
    private String name;

    public Mall(String name) {
        this.name = name;
    }

    public double getPrice(String product) {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        return Math.floor((ThreadLocalRandom.current().nextDouble() * 2) * 100) / 100 + product.charAt(0);
        return ThreadLocalRandom.current().nextDouble() * 2 + product.charAt(0);
    }
}
