package com.jinx.test.cas;

import com.jinx.tool.JinxLogger;
import com.jinx.tool.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

public class CasTest {
    private final static JinxLogger log = LoggerFactory.getLogger(CasTest.class);

    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(5);
        log.info(atomicInteger.compareAndSet(5, 2024) + "\t" + atomicInteger.get());
        log.info(atomicInteger.compareAndSet(5, 2022) + "\t" + atomicInteger.get());
    }
}
