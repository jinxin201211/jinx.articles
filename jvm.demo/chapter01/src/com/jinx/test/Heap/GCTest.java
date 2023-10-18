package com.jinx.test.Heap;

import java.util.ArrayList;
import java.util.List;

public class GCTest {
    public static void main(String[] args) {
        List<String> arr = new ArrayList<>();
        String a = "Hello World!!!";
        int i = 0;
        while (true) {
            arr.add(a);
            a += a;
            i++;
        }
    }
}
