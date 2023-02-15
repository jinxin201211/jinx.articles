package com.jinx.testnetty;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.atomic.AtomicInteger;

public class _011TestWalkFileTreeDelete {
    public static void main(String[] args) throws IOException {
        Path path = Paths.get("D:/Program Files/Clash-Windows - 副本");
        Files.walkFileTree(path, new SimpleFileVisitor<Path>() {

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                System.out.println("enter ============> " + dir);
                return super.preVisitDirectory(dir, attrs);
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                System.out.println("file ============> " + file);
                Files.delete(file);
                return super.visitFile(file, attrs);
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                System.out.println("exit ============> " + dir);
                Files.delete(dir);
                return super.postVisitDirectory(dir, exc);
            }

        });
    }
}
