package com.jinx.test.future;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class CompletableFutureMallDemo {
    static List<Mall> malls = Arrays.asList(new Mall("京东"), new Mall("淘宝"), new Mall("拼多多"));

    private static List<String> getPrices(List<Mall> malls, String product) {
        return malls
                .stream()
                .map(p ->
                        String.format("在%s购物，商品%s的价格是：%.2f",
                                p.getName(),
                                product,
                                p.getPrice(product)))
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        String product = "iPhoneX";

//        Mall jingdong = new Mall("京东");
//        Mall taobao = new Mall("淘宝");
//        Mall pinduoduo = new Mall("拼多多");
//
//        double priceJingdong = jingdong.getPrice(product);
//        double priceTaobao = taobao.getPrice(product);
//        double pricePinduoduo = pinduoduo.getPrice(product);
//
//        System.out.println("在" + jingdong.getName() + "购物，商品" + product + "的价格是："
//                + priceJingdong);
//        System.out.println("在" + taobao.getName() + "购物，商品" + product + "的价格是："
//                + priceTaobao);
//        System.out.println("在" + pinduoduo.getName() + "购物，商品" + product + "的价格是："
//                + pricePinduoduo);
        getPrices(malls, product).forEach(System.out::println);
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
