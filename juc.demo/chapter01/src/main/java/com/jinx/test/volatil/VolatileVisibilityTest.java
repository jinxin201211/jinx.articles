package com.jinx.test.volatil;

import com.jinx.test.utils.JinxLogger;
import com.jinx.test.utils.LoggerFactory;
import com.jinx.test.utils.TestUtil;

public class VolatileVisibilityTest {
    private static final JinxLogger log = LoggerFactory.getLogger(VolatileVisibilityTest.class);

    private static boolean flag = true;

    public static void main(String[] args) {
        new Thread(() -> {
            log.info("come in");
            while (flag) {
                log.info("flag is false, thread stopped");
                System.out.println("x");
            }
            log.info("flag is false, thread stopped");
        }, "t1").start();

        TestUtil.sleep(2000L);

        flag = false;
        log.info("flag changed to false");
        ;
    }
}
