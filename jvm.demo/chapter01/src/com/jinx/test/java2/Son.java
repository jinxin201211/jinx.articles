package com.jinx.test.java2;

class Father {
    Father() {
        System.out.println("Father Constructor");
    }

    public static void showStatic() {
        System.out.println("Father Static");
    }

    public final void showFinal() {
        System.out.println("Father Final");
    }

    public void showCommon() {
        System.out.println("Father Common");
    }
}

interface MethodInterface {
    void showInterface();
}

public class Son extends Father {
    Son() {
        System.out.println("Son Constructor");
    }

    public static void showStatic() {
        System.out.println("Son Static");
    }

    private void showPrivate() {
        System.out.println("Son Private");
    }

    public void showCommon() {
        System.out.println("Son Common");
    }

    private void showPrivateInfo() {
        System.out.println("Son Private Info");
    }

    public void showPublicInfo() {
        System.out.println("Son Public Info");
    }

    public void show() {
        showStatic();//invokestatic
        super.showStatic();//invokestatic
        showPrivate();//invokespecial
        showCommon();//invokevirtual
        super.showCommon();//invokespecial
        showFinal();//invokevirtual
        super.showFinal();//invokespecial
        showPrivateInfo();//invokespecial
        showPublicInfo();//invokevirtual

        MethodInterface methodInterface = null;
        methodInterface.showInterface();//invokeinterface
    }

    public static void main(String[] args) {
        Son son = new Son();//invokespecial
        son.show();
    }
}
