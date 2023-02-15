package com.jinx.testnetty;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

@Slf4j
public class _009TestFileChannelTransferTo {
    public static void main(String[] args) {
        try (
//                FileChannel from = new FileInputStream("src/main/resources/from.txt").getChannel();
                FileChannel from = new FileInputStream("D:\\Download\\cn_windows_7_professional_with_sp1_x86_dvd_u_677162.iso").getChannel();
                FileChannel to = new FileOutputStream("src/main/resources/to.txt").getChannel();
        ) {
            log.info("开始拷贝");
            long size = from.size();
            //transferTo的零拷贝优化，每次都最多传输2G，所以对于超过2G的文件，要分多次传输
            for (long left = size; left > 0; ) {
                left -= from.transferTo(size - left, left, to);//零拷贝优化。上限2G
            }
            log.info("拷贝完成");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
