package com.jinx.test.gc;

public class SystemGCTest {

    public static void main(String[] args) {
        int ARRAY_LENGTH = Integer.MAX_VALUE;
        System.out.println(ARRAY_LENGTH);
        SystemGCTest[] tremendous = new SystemGCTest[ARRAY_LENGTH];
        tremendous[(ARRAY_LENGTH - 1)] = new SystemGCTest();
//        new SystemGCTest();
//        System.gc();
//
//        System.runFinalization();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("SystemGCTest 被回收了");
    }
}
