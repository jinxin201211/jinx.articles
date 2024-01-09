package com.jinx.test.lock;

public class InterruptTest4 {
    public static void main(String[] args) {
        System.out.println("[" + Thread.currentThread().getName() + "]" + Thread.interrupted());//false
        System.out.println("[" + Thread.currentThread().getName() + "]" + Thread.interrupted());//false
        System.out.println("[" + Thread.currentThread().getName() + "]" + "INTERRUPT-----");
        Thread.currentThread().interrupt();
        System.out.println("[" + Thread.currentThread().getName() + "]" + "INTERRUPT-----");
        System.out.println("[" + Thread.currentThread().getName() + "]" + Thread.interrupted());//true
        System.out.println("[" + Thread.currentThread().getName() + "]" + Thread.interrupted());//false


        System.out.println("[" + Thread.currentThread().getName() + "]" + Thread.currentThread().isInterrupted());//false
        System.out.println("[" + Thread.currentThread().getName() + "]" + Thread.currentThread().isInterrupted());//false
        System.out.println("[" + Thread.currentThread().getName() + "]" + "INTERRUPT-----");
        Thread.currentThread().interrupt();
        System.out.println("[" + Thread.currentThread().getName() + "]" + "INTERRUPT-----");
        System.out.println("[" + Thread.currentThread().getName() + "]" + Thread.currentThread().isInterrupted());//true
        System.out.println("[" + Thread.currentThread().getName() + "]" + Thread.currentThread().isInterrupted());//true
    }
}
