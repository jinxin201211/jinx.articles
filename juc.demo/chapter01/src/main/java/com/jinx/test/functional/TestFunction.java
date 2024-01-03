package com.jinx.test.functional;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TestFunction {
    public static void main(String[] args) {
//        testCustomConsumer();
//        testOfNullable();
        testFunctionIdentity();
    }

    private static void testCustomConsumer() {
        System.out.println(Date.class.getName());
        ICustomConsumer<Integer> consumer = System.out::println;
        consumer.consume(0);

        Consumer<Integer> consumer1 = System.out::println;
        consumer1.accept(123);

        Function<Integer, String> function = x -> (x * 10 + 4) + "";
        System.out.println(function.apply(123));
    }

    private static void testOfNullable() {
        Map<Integer, Function<Integer, String>> map = new HashMap<>();
        map.put(1, x -> String.valueOf((char) (x + 40)));
        map.put(2, x -> String.valueOf((char) (x + 50)));
        map.put(3, x -> String.valueOf((char) (x + 60)));
        map.put(4, x -> String.valueOf((char) (x + 70)));
        map.put(-1, x -> String.valueOf((char) (x + 80)));
        System.out.println(Optional.ofNullable(map.get(1)).orElse(map.get(-1)).apply(1));
        System.out.println(Optional.ofNullable(map.get(2)).orElse(map.get(-1)).apply(1));
        System.out.println(Optional.ofNullable(map.get(3)).orElse(map.get(-1)).apply(1));
        System.out.println(Optional.ofNullable(map.get(4)).orElse(map.get(-1)).apply(1));
        System.out.println(Optional.ofNullable(map.get(5)).orElse(map.get(-1)).apply(1));
    }

    private static void testFunctionIdentity() {
        List<User> users = new ArrayList<>();
        users.add(new User(1, "WangLuoSheBei"));
        users.add(new User(2, "AnQuanSheBei"));
        users.add(new User(3, "ZhuJiSheBei"));
        users.add(new User(3, "CunChuSheBei"));
        users.add(new User(2, "ShuJuKu"));
        users.add(new User(2, "ZhonJianJian"));
        Map<Integer, User> collect = users.stream().collect(Collectors.toMap(User::getId, Function.identity(), (x, y) -> y));
        for (Map.Entry<Integer, User> entry : collect.entrySet()) {
            System.out.println(entry.getKey());
            System.out.println(entry.getValue());
            System.out.println("---------------");
        }
    }
}
