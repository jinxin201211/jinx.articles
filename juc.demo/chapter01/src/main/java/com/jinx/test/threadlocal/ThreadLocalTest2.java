package com.jinx.test.threadlocal;

import com.jinx.tool.JinxLogger;
import com.jinx.tool.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadLocalTest2 {
    private final static JinxLogger log = LoggerFactory.getLogger(ThreadLocalTest2.class);

    public static void main(String[] args) {
        ThreadLocalData data = new ThreadLocalData();
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        try {
            for (int i = 0; i < 100; i++) {
                executorService.submit(() -> {
                    try {
                        int before = data.num.get();
                        data.add();
                        int after = data.num.get();
                        log.info("before: {}, after: {}", before, after);
                    } finally {
                        data.num.remove();
                    }
                });
            }
        } finally {
            executorService.shutdown();
        }
    }
}

class ThreadLocalData {
    ThreadLocal<Integer> num = ThreadLocal.withInitial(() -> 0);

    public synchronized void add() {
        num.set(num.get() + 1);
    }
}

