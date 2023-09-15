package com.jinx.test.jvm;

public class LocalVariablesSlotTest {
    public int num = 0;

    public LocalVariablesSlotTest() {
        System.out.println(num);
    }

    public void printNum() {
        int count = 0;
        int sum = 0;
        for (int i = 0; i < 10; i++) {
            if (i == 0) {
                count = 0;
                sum = count + 1;
            } else if (i == 1) {
                count = 1;
                sum = count + 1;
            } else {
                int temp = sum;
                sum = count + temp;
                count = temp;
            }
        }
        double sum_1_2 = sum * 1.2;
        System.out.println(this.num);
        System.out.println(sum);
    }

    public void testReuse() {
        int a = 0;
        {
            int b = 0;
            b = a + 1;
        }
        int c = a + 1;
    }

    public static void main(String[] args) {
        LocalVariablesSlotTest localVariablesSlotTest = new LocalVariablesSlotTest();
        localVariablesSlotTest.printNum();
    }
}
