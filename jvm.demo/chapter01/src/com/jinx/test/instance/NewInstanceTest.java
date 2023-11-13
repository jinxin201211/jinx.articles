package com.jinx.test.instance;

import java.lang.reflect.InvocationTargetException;

public class NewInstanceTest implements Cloneable {
    private String value;

    private Integer number;

    public NewInstanceTest() {
        value = "New Instance %d";
        number = 0;
    }

    public NewInstanceTest(String val) {
        value = val;
        number = 0;
    }

    public NewInstanceTest(String val, Integer num) {
        value = val;
        number = num;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        NewInstanceTest newInstanceTest = (NewInstanceTest) super.clone();
        newInstanceTest.number++;
        return newInstanceTest;
    }

    public static void main(String[] args) throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, CloneNotSupportedException {
        NewInstanceTest newInstanceTest1 = new NewInstanceTest();
        System.out.printf((newInstanceTest1.value) + "%n", newInstanceTest1.number);

        NewInstanceTest newInstanceTest2 = NewInstanceTest.class.newInstance();
        System.out.printf((newInstanceTest2.value) + "%n", newInstanceTest2.number);

        NewInstanceTest newInstanceTest3 = NewInstanceTest.class.getConstructor(String.class).newInstance("New Instance with Params %d");
        System.out.printf((newInstanceTest3.value) + "%n", newInstanceTest3.number);

        NewInstanceTest newInstanceTest4 = NewInstanceTest.class.getConstructor(String.class, Integer.class).newInstance("New Instance with Params %d", 13306);
        System.out.printf((newInstanceTest4.value) + "%n", newInstanceTest4.number);

        NewInstanceTest newInstanceTest5 = (NewInstanceTest) newInstanceTest4.clone();
        System.out.printf((newInstanceTest5.value) + "%n", newInstanceTest5.number);
    }
}
