package com.jinx.test.MethodArea;

import jdk.internal.org.objectweb.asm.ClassWriter;
import jdk.internal.org.objectweb.asm.Opcodes;

public class OOMMetaspaceTest extends ClassLoader {
    public static void main(String[] args) {
        int j = 0;
        OOMMetaspaceTest test = new OOMMetaspaceTest();
        try {
            while (true) {
                ClassWriter writer = new ClassWriter(0);
                writer.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC, "Class" + j, null, "java/lang/Object", null);
                byte[] code = writer.toByteArray();
                test.defineClass("Class" + j, code, 0, code.length);
                j++;
            }
        } finally {
            System.out.println(j);
        }
    }
}
