package com.jinx.testnio;

import java.nio.ByteBuffer;

import static com.jinx.nettydemo.base.ByteBufferUtil.debugAll;

public class _008TestByteBufferExam {
    public static void main(String[] args) {
        ByteBuffer source = ByteBuffer.allocate(32);
        source.put("Hello world!\nI'm zhangsan\nHo".getBytes());
        split1(source);
        source.put("w are you!\n".getBytes());
        split1(source);
    }

    public static void split1(ByteBuffer source) {
        source.flip();
        for (int i = 0; i < source.limit(); i++) {
            if ((char) source.get(i) == '\n') {
                int length = i + 1 - source.position();
                ByteBuffer target = ByteBuffer.allocate(length);
                for (int j = 0; j < length; j++) {
                    target.put(source.get());
                }
                debugAll(target);
            }
        }
        source.compact();
    }

    public static void split(ByteBuffer source) {
        source.flip();
        for (int i = 0; i < source.limit(); i++) {
            if (source.get(i) == '\n') {
                int length = i + 1 - source.position();
                ByteBuffer target = ByteBuffer.allocate(length);
                for (int j = 0; j < length; j++) {
                    target.put(source.get());
                }
                debugAll(target);
            }
        }
        source.compact();
    }
}
