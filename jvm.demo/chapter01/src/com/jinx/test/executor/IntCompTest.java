package com.jinx.test.executor;

/**
 * -Xint 6520ms
 * -Xcomp 603ms
 * -Xmixed 622ms
 */
public class IntCompTest {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        testPrimeNumber(1000000);
        long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - start) + "ms");
    }

    public static void testPrimeNumber(int count) {
        for (int i = 0; i < count; i++) {
            howmuch:
            for (int j = 2; j <= 100; j++) {
                for (int k = 2; k <= Math.sqrt(j); k++) {
                    if (j % k == 0) {
                        continue howmuch;
                    }
                }
            }
        }
    }
}
