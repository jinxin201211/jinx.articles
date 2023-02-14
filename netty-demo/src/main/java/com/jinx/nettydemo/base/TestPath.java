package com.jinx.nettydemo.base;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TestPath {
    public static void main(String[] args) {
        Path path = Paths.get("D:\\Repository\\Test\\nettt-demo");
        System.out.println(path.getFileSystem());
        System.out.println(path);
        path.normalize();
        System.out.println(path);

        File file = new File("D:\\Repository\\Test\\nettt-demo\\pom.xml");
        System.out.println(file.exists());
    }
}
