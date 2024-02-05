package com.jinx.test.synchronizd;

import org.openjdk.jol.info.ClassLayout;

public class SynchronizedUpTest {
    public static void main(String[] args) {
//        TestUtil.sleep(6000L);
        Object obj = new Object();
//        System.out.println(obj.hashCode());
//        System.out.println(Integer.toHexString(obj.hashCode()));
//        System.out.println(Integer.toBinaryString(obj.hashCode()));
//        System.out.println(ClassLayout.parseInstance(obj).toPrintable());

        synchronized (obj) {
            System.out.println(ClassLayout.parseInstance(obj).toPrintable());
        }

        Customer customer = new Customer();
        System.out.println(ClassLayout.parseInstance(customer).toPrintable());
    }
}

class Customer {
}
