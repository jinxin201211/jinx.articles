package com.jinx.test.gc;

public class LocalVarGC {
    public void localVarGC1() {
        byte[] buffer = new byte[10 * 1024 * 1024];
        System.gc();
    }

    public void localVarGC2() {
        byte[] buffer = new byte[10 * 1024 * 1024];
        buffer = null;
        System.gc();
    }

    public void localVarGC3() {
        {
            byte[] buffer = new byte[10 * 1024 * 1024];
        }
        System.gc();
    }

    public void localVarGC4() {
        {
            byte[] buffer = new byte[10 * 1024 * 1024];
        }
        int value = 10;
        System.gc();
    }

    public void localVarGC5() {
        localVarGC1();
        System.gc();
    }

    public static void main(String[] args) {
        LocalVarGC localVarGC = new LocalVarGC();
        localVarGC.localVarGC5();
    }
}
