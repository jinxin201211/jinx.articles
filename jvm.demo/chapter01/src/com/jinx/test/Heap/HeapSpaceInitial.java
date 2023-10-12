package com.jinx.test.Heap;

public class HeapSpaceInitial {
    public static void main(String[] args) throws InterruptedException {
        long maxMemory = Runtime.getRuntime().maxMemory() / 1024 / 1024;
        long initialMemory = Runtime.getRuntime().totalMemory() / 1024 / 1024;

//        Thread.sleep(1000000l);

        System.out.println("maxMemory = " + maxMemory + "M");
        System.out.println("initialMemory = " + initialMemory + "M");
        System.out.println("maxMemory - initialMemory = " + (maxMemory - initialMemory) + "M");
        System.out.println("-------------------------------------------------------");

        System.out.println("系统内存大小为：" + initialMemory * 64 + "M");
        System.out.println("系统内存大小为：" + maxMemory * 4 + "M");
        System.out.println("-------------------------------------------------------");

    }
}
