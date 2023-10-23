package com.jinx.test.Heap;

public class StackAllocateTest {
    /**
     * The main program.
     *
     * The code calls the following statement:
     * <pre>{@code
     *   System.out.println("Hello, World!");
     * }</pre>
     */
    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        for (int i = 0; i < 10000000; i++) {
            alloc();
        }

        long end = System.currentTimeMillis();
        System.out.println("花费的时间： " + (end - start) + "ms");

        try {
            Thread.sleep(1000000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void alloc() {
        User user = new User();
    }

    public static class User {
        public String name;
    }
}
