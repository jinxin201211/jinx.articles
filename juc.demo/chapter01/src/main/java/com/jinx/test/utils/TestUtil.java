package com.jinx.test.utils;

import java.util.concurrent.TimeUnit;

public class TestUtil {
    public static void sleep() {
        sleep(1000l);
    }

    public static void sleep(long millis) {
        try {
            TimeUnit.MILLISECONDS.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
