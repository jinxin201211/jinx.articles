package com.jinx.nettydemo.nio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

import static com.jinx.nettydemo.base.ByteBufferUtil.debugRead;

@Slf4j
public class Server {
    public static void main(String[] args) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(16);
        ServerSocketChannel server = ServerSocketChannel.open();
        server.configureBlocking(false); //非阻塞模式

        server.bind(new InetSocketAddress(8899));
        List<SocketChannel> channels = new ArrayList<>();
        while (true) {
//            log.debug("connectting……");
            SocketChannel channel = server.accept();
            if (channel != null) {
                log.debug("connectted! {}", channel);
                channel.configureBlocking(false);
                channels.add(channel);
            }

            for (SocketChannel cnl : channels) {
//                log.debug("before read {}", cnl);
                int read = cnl.read(buffer);
                if (read > 0) {
                    buffer.flip();
                    debugRead(buffer);
                    buffer.clear();
                    log.debug("after read {}", cnl);
                }
            }
        }
    }
}
