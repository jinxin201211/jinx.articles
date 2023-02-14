package com.jinx.nettydemo.base;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.atomic.AtomicInteger;

public class TestWalkFileTree {
    public static void main(String[] args) throws IOException {
        Path path = Paths.get("D:\\Download");
        AtomicInteger dirCount = new AtomicInteger();
        AtomicInteger fileCount = new AtomicInteger();
        Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
//                System.out.println(dir);
                dirCount.incrementAndGet();
                return super.preVisitDirectory(dir, attrs);
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
//                file.toString()
                if (file.toString().endsWith(".zip")) {
                    System.out.println("压缩文件:" + file);
                } else {
//                    System.out.println(file);
                }
                fileCount.incrementAndGet();
                return super.visitFile(file, attrs);
            }
        });

        System.out.println(dirCount + ", " + fileCount);
    }
}
