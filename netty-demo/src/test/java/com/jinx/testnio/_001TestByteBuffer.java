package com.jinx.testnio;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

@Slf4j
public class _001TestByteBuffer {
    private static Logger logger = LoggerFactory.getLogger("mytest");
    private static Logger logger1 = LoggerFactory.getLogger("mytest1");
    private static Logger logger2 = LoggerFactory.getLogger("mytest2");

    public static void main(String[] args) {
        try (FileChannel channel = new FileInputStream("src/main/resources/data.txt").getChannel()) {
            ByteBuffer byteBuffer = ByteBuffer.allocate(5);
//            channel.read(byteBuffer);
//            channel.read(byteBuffer);
//            channel.read(byteBuffer);
//            ByteBufferUtil.debugAll(byteBuffer);
            while (channel.read(byteBuffer) != -1) {
//                channel.read(byteBuffer);
                byteBuffer.flip(); // 切换为读
                while (byteBuffer.hasRemaining()) {
                    byte b = byteBuffer.get();
//                    log.debug("读取到的字节：{}", b);
//                    logger.debug("logger读取到的字节：{}", b);
//                    logger1.debug("logger1读取到的字节：{}", b);
//                    logger2.debug("logger2读取到的字节：{}", b);
                    System.out.println((char) b);
                }
                byteBuffer.clear(); // 切换为写
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
