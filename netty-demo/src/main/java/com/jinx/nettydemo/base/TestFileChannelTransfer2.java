package com.jinx.nettydemo.base;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class TestFileChannelTransfer2 {
    public static void main(String[] args) {
        try (
//                FileChannel from = new FileInputStream("src/main/resources/from.txt").getChannel();
                FileChannel from = new FileInputStream("D:\\Download\\cn_windows_7_professional_with_sp1_x86_dvd_u_677162.iso").getChannel();
                FileChannel to = new FileOutputStream("src/main/resources/to.txt").getChannel();
        ) {
            long size = from.size();
            for (long left = size; left > 0; ) {
                left -= from.transferTo(size - left, left, to);//零拷贝优化。上限2G
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
