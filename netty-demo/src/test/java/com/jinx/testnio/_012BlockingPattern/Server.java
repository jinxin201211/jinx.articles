package com.jinx.testnio._012BlockingPattern;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

import static com.jinx.testnio.ByteBufferUtil.debugRead;

@Slf4j
public class Server {

    public static void main(String[] args) throws IOException {
        ServerSocketChannel server = ServerSocketChannel.open();
        server.bind(new InetSocketAddress(2233));
        server.configureBlocking(false);
        List<SocketChannel> channels = new ArrayList<>();
        ByteBuffer buffer = ByteBuffer.allocate(16);
        while (true) {
//            log.info("connectting...");
            SocketChannel channel = server.accept();
            if (channel != null) {
                log.info("connectted... {}", channel);
                channel.configureBlocking(false);
                channels.add(channel);
            }
            for (SocketChannel sc : channels) {
//                log.info("before read... {}", sc);
                int read = sc.read(buffer);
                if (read != 0) {
                    buffer.flip();
                    debugRead(buffer);
                    buffer.clear();
                    log.info("after read... {}", sc);
                }
            }
        }
    }

}
