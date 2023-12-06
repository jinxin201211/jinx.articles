package com.jinx.test.gc;

public class ForLabelTest {
    public static void main(String[] args) {
        LaBeLX:
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (i > 5 && j > 5) {
                    continue LaBeLX;
                }
                System.out.println("i = " + i + " j = " + j);
            }
        }
    }
}
