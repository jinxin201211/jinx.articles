package com.jinx.test.jvm;

import com.sun.java.accessibility.AccessBridge;
import sun.misc.Launcher;

import java.net.URL;
import java.security.Provider;
import java.util.Arrays;

//Shift + Shift 全局搜索， Ctrl + H 打开Hierarchy， Ctrl + Alt + T 代码环绕
public class ClassLoaderTest {
    public static void main(String[] args) {
        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        System.out.println(systemClassLoader); //sun.misc.Launcher$AppClassLoader@18b4aac2

        ClassLoader extClassLoader = systemClassLoader.getParent();
        System.out.println(extClassLoader); //sun.misc.Launcher$ExtClassLoader@1b6d3586

        //获取不到引导类加载器
        ClassLoader bootstrapClassLoader = extClassLoader.getParent();
        System.out.println(bootstrapClassLoader); //null

        //对于用户自定义类，默认使用系统类加载器加载
        ClassLoader classLoader = ClassLoaderTest.class.getClassLoader();
        System.out.println(classLoader); //sun.misc.Launcher$AppClassLoader@18b4aac2

        URL[] urLs = Launcher.getBootstrapClassPath().getURLs();
        for (URL url : urLs) {
            System.out.println(url);
        }

        ClassLoader providerClassLoader = Provider.class.getClassLoader();
        System.out.println(providerClassLoader);

        String extdirs = System.getProperty("java.ext.dirs");
        Arrays.stream(extdirs.split(";")).forEach(System.out::println);

        ClassLoader accessBridgeClassLoader = AccessBridge.class.getClassLoader();
        System.out.println(accessBridgeClassLoader);

    }
}
