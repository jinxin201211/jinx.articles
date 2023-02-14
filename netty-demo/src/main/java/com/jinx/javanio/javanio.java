package com.jinx.javanio;

import java.nio.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class javanio {
    public static void main(String[] args) {
//        ByteBuffer buffer = ByteBuffer.allocate(10);
//        buffer.put((byte) 'H').put((byte) 'e').put((byte) 'l').put((byte) 'l').put((byte) 'o').put((byte) 'w');
//        buffer.flip();
//        System.out.println((char) buffer.get());
//        System.out.println((char) buffer.get());
//        System.out.println((char) buffer.get());
//        System.out.println((char) buffer.get());
//        System.out.println((char) buffer.get());
//        System.out.println((char) buffer.get());
//        buffer.clear();
//        System.out.println(buffer.position(6));
//        buffer.put((byte) '!');
//        buffer.flip();
//        System.out.println(buffer.position());
////        buffer.reset();
//        System.out.println((char) buffer.get());
//        System.out.println((char) buffer.get());
//        System.out.println((char) buffer.get());
//        System.out.println((char) buffer.get());
//        System.out.println((char) buffer.get());
//        System.out.println((char) buffer.get());
//        System.out.println((char) buffer.get());


//        Map<String, String> map = new HashMap<>();
//        map.put("kkkk", "vvvv");
//        System.out.println(Optional.ofNullable(map.get("kkkk")).map(Object::toString).orElse(""));
//        System.out.println(Optional.ofNullable(map.get("cccc")).map(Object::toString).orElse("121"));

//        CharBuffer charBuffer = CharBuffer.allocate(10);
//        charBuffer.put('H');
//        charBuffer.flip();
//        System.out.println(charBuffer.get());
//        IntBuffer intBuffer = IntBuffer.allocate(10);
//        intBuffer.put(42);
//        intBuffer.flip();
//        System.out.println((char) intBuffer.get());
//        DoubleBuffer doubleBuffer = DoubleBuffer.allocate(10);
//        doubleBuffer.put(43);
//        doubleBuffer.flip();
//        System.out.println((char) doubleBuffer.get());
//        ShortBuffer shortBuffer = ShortBuffer.allocate(10);
//        shortBuffer.put((short) 44);
//        shortBuffer.flip();
//        System.out.println((char) shortBuffer.get());
//        LongBuffer longBuffer = LongBuffer.allocate(10);
//        longBuffer.put(42);
//        longBuffer.flip();
//        System.out.println((char) longBuffer.get());
//        FloatBuffer floatBuffer = FloatBuffer.allocate(10);
//        floatBuffer.put(45);
//        floatBuffer.flip();
//        System.out.println((char) floatBuffer.get());
//        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
//        byteBuffer.put((byte) '*');
//        byteBuffer.flip();
//        System.out.println(byteBuffer.get());

//        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
//        byteBuffer.put((byte) 16635);
//        byteBuffer.flip();
//        System.out.println((char) byteBuffer.get());

//        for (int j = 0; j < 100; j++) {
//            System.out.printf("rownum " + j + 1 + ":");
//            for (int i = 0; i < 100; i++) {
//                System.out.print(" " + (char) (j * 100 + i));
//            }
//            System.out.println();
//        }

//        a1b1 = 3;
//        a1b2 = 5;
//        a2c1 = 6;
//        a2c2 = 4;
//        b2 - c2 = c1 - b1;
//        a1(b2 - c2) + a2(b2 - c2) = 2;

//        char_to_byte();
        wrap_buffer();
    }

    static void char_to_byte() {
        char chr = '1';
        byte bt = Byte.valueOf(Character.toString(chr));
        System.out.println(bt);
    }

    static void wrap_buffer() {
        int[] array = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        IntBuffer intBuffer = IntBuffer.wrap(array);
        intBuffer.put(10);
        intBuffer.put(11);
        intBuffer.put(12);
        intBuffer.flip();
        System.out.println(array.length);
        System.out.println(array[0]);
        System.out.println(array[1]);
        System.out.println(array[2]);
//        System.out.println(intBuffer.get(0));
//        System.out.println(intBuffer.get(1));
//        System.out.println(intBuffer.get(2));
//        System.out.println(intBuffer.get(3));
//        System.out.println(intBuffer.get(4));
//        System.out.println(intBuffer.get(5));
//        System.out.println(intBuffer.get(6));
//        System.out.println(intBuffer.get(7));
//        System.out.println(intBuffer.get(8));
//        System.out.println(intBuffer.get(9));
    }
}
