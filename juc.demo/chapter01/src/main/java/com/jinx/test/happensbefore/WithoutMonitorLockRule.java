package com.jinx.test.happensbefore;

import com.jinx.test.utils.JinxLogger;
import com.jinx.test.utils.LoggerFactory;
import com.jinx.test.utils.TestUtil;

import java.util.concurrent.locks.ReentrantLock;

public class WithoutMonitorLockRule {
    private static boolean stop = false;

    private static JinxLogger logger = LoggerFactory.getLogger(WithoutMonitorLockRule.class);

    public static void main(String[] args) {
//        testWithoutRule();
//        testWithSynchronized();
//        testWithReentrylock();
//        testWithVolatile();
        testWithVolatile2();
//        testWithThreadStart();
    }

    private static void testWithoutRule() {
        Thread updater = new Thread(() -> {
            TestUtil.sleep(1000);
            stop = true;
            logger.info("updater set stop true");
        }, "updater");

        Thread getter = new Thread(() -> {
            while (true) {
                if (stop) {
                    logger.info("getter stopped");
                    break;
                }
            }
        }, "getter");
        getter.start();
        updater.start();
    }

    private static void testWithSynchronized() {
        Thread updater = new Thread(() -> {
            TestUtil.sleep(1000);
            synchronized (logger) {
                stop = true;
                logger.info("updater set stop true");
            }
        }, "updater");

        Thread getter = new Thread(() -> {
            while (true) {
                synchronized (logger) {
                    if (stop) {
                        logger.info("getter stopped");
                        break;
                    }
                }
            }
        }, "getter");
        getter.start();
        updater.start();
    }

    private static ReentrantLock lock = new ReentrantLock();

    private static void testWithReentrylock() {
        Thread updater = new Thread(() -> {
            TestUtil.sleep(1000);
            lock.lock();
            stop = true;
            logger.info("updater set stop true");
            lock.unlock();
        }, "updater");

        Thread getter = new Thread(() -> {
            while (true) {
                lock.lock();
                if (stop) {
                    logger.info("getter stopped");
                    break;
                }
                lock.unlock();
            }
        }, "getter");
        getter.start();
        updater.start();
    }

    private static volatile Boolean stop1 = false;

    private static void testWithVolatile() {
        Thread updater = new Thread(() -> {
            TestUtil.sleep(1000);
            stop1 = true;
            logger.info("updater set stop true");
        }, "updater");

        Thread getter = new Thread(() -> {
            while (true) {
                if (stop1) {
                    logger.info("getter stopped");
                    break;
                }
            }
        }, "getter");
        getter.start();
        updater.start();
    }

    private static Boolean stop2 = false;

    private static volatile Object obj = new Object();

    private static void testWithVolatile2() {
        Thread updater = new Thread(() -> {
            TestUtil.sleep(1000);
            stop2 = true;
//            obj = new Object();
            logger.info("updater set stop true");
        }, "updater");

        Thread getter = new Thread(() -> {
            while (true) {
//                Object newObj = WithoutMonitorLockRule.obj;
                obj = new Object();
                if (stop2) {
                    logger.info("getter stopped");
                    break;
                }
            }
        }, "getter");
        getter.start();
        updater.start();
    }

    private static void testWithThreadStart() {
        Thread getter = new Thread(() -> {
            while (true) {
                if (stop) {
                    logger.info("getter stopped");
                    break;
                }
            }
        }, "getter");
        Thread updater = new Thread(() -> {
            TestUtil.sleep(1000);
            stop = true;
            getter.start();
            logger.info("updater set stop true");
        }, "updater");
        updater.start();
    }
}
