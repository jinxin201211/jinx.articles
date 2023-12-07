package com.jinx.test.gc;

import java.lang.ref.WeakReference;

public class WeakReferenceTest {
    public static void main(String[] args) {
        WeakReference<User> wref = new WeakReference<>(new User(1, "Kevin"));
        System.out.println(wref.get());
        System.gc();
        System.out.println(wref.get());
    }

    public static class User {
        int age;
        String name;

        public User(int age, String name) {
            this.age = age;
            this.name = name;
        }
    }
}
