package com.jinx.test.threadlocal;

import com.jinx.tool.JinxLogger;
import com.jinx.tool.LoggerFactory;

import java.util.Random;

public class ThreadLocalTest3 {
    private final static JinxLogger log = LoggerFactory.getLogger(ThreadLocalTest3.class);

    public static void main(String[] args) {
        ThreadLocal<String> tl = new ThreadLocal<>();
        tl.set("hello");
        System.out.println(tl.get());
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                tl.set(new Random().nextInt(10000) + "");
                log.info("{}", tl.get());
            }).start();
        }
    }
}
