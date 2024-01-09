package com.jinx.test.utils;

import java.util.Date;
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

    public static void log(String log) {
        System.out.printf("[\033[034m%s\033[038m] [\033[036m%s\033[038m] [\033[032m%s\033[038m] %s\n", DateUtil.toDateString(new Date(), "YYYY-MM-DD HH:mm:ss.SSS"), getCallingClassName(), Thread.currentThread().getName(), log);
    }

    private static String getCallingClassName() {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        for (int i = stackTraceElements.length - 1; i >= 0; i--) {
            if (stackTraceElements[i - 1].getClassName().equals(TestUtil.class.getName())) {
                return stackTraceElements[i].getClassName();
            }
        }
        return stackTraceElements[stackTraceElements.length - 1].getClassName();
    }
}
