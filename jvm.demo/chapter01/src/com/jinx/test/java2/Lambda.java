package com.jinx.test.java2;

import java.util.function.Function;

public class Lambda {
    public void lam(Function func) {
        return;
    }

    public static void main(String[] args) {
        Function<String, Boolean> func = s -> {
            return true;
        };

        Lambda lambda = new Lambda();
        lambda.lam(func);
        lambda.lam((s) -> {
            return true;
        });
    }
}
