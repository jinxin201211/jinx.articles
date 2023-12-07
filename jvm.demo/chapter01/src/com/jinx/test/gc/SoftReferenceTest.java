package com.jinx.test.gc;

import java.lang.ref.SoftReference;

public class SoftReferenceTest {
    public static void main(String[] args) {
        SoftReference<Object> sref = new SoftReference<>(new User(1, "Kevin`"));
        System.out.println(sref.get());
        System.gc();
        try {
            Thread.sleep(3000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(sref.get());

        try {
            byte[] bytes = new byte[1024 * 1024 * 7];
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            System.out.println(sref.get());
        }

    }

    public static class User {
        int age;
        String name;

        public User(int age, String name) {
            this.age = age;
            this.name = name;
        }

        @Override
        public String toString() {
            return "User{" +
                    "age=" + age +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
}
