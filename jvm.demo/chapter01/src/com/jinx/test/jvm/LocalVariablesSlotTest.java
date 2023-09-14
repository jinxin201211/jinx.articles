package com.jinx.test.jvm;

public class LocalVariablesSlotTest {
    public int num = 0;

    public LocalVariablesSlotTest() {
        System.out.println(num);
    }

    public void printNum() {
        System.out.println(this.num);
    }

    public static void main(String[] args) {
        LocalVariablesSlotTest localVariablesSlotTest = new LocalVariablesSlotTest();
        localVariablesSlotTest.printNum();
    }
}
