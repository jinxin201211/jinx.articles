package com.jinx.test.gc;

public class FinalizeTest {
    static FinalizeTest obj;

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("finalize()");
        obj = this;
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("test");
        obj = new FinalizeTest();
        // 对象第一次拯救自己
        System.out.println("first");
        obj = null;
        System.gc();
        Thread.sleep(2000L);
        if (obj != null) {
            System.out.println("alive");
        } else {
            System.out.println("killed");
        }

        // 对象第二次拯救自己
        System.out.println("first");
        obj = null;
        System.gc();
        Thread.sleep(2000L);
        if (obj != null) {
            System.out.println("alive");
        } else {
            System.out.println("killed");
        }
    }
}
