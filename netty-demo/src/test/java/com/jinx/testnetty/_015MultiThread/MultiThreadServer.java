package com.jinx.testnetty._015MultiThread;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

import static com.jinx.testnetty.ByteBufferUtil.debugAll;

@Slf4j
public class MultiThreadServer {
    public static void main(String[] args) throws IOException {
        Thread.currentThread().setName("boss");
        ServerSocketChannel bossChannel = ServerSocketChannel.open();
        bossChannel.configureBlocking(false);
        Selector selector = Selector.open();
        bossChannel.register(selector, SelectionKey.OP_ACCEPT, null);
        bossChannel.bind(new InetSocketAddress(2234));

//        Worker worker0 = new Worker("worker-0");
        Worker[] workers = new Worker[2];
        for (int i = 0; i < 2; i++) {
            workers[i] = new Worker("worker-" + i);
        }
        AtomicInteger workerIndex = new AtomicInteger(0);
//        worker0.register();
        while (true) {
            selector.select();
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                iterator.remove();
                if (selectionKey.isAcceptable()) {
                    ServerSocketChannel serverChannel = (ServerSocketChannel) selectionKey.channel();
                    SocketChannel accept = serverChannel.accept();
                    accept.configureBlocking(false);
                    log.debug("========> connected {}", accept);
                    log.debug("========> before register {}", accept.getRemoteAddress());
                    //round robin
                    workers[workerIndex.getAndIncrement() % workers.length].register(accept);
//                    worker0.register(accept);
//                    accept.register(worker0.selector, SelectionKey.OP_READ, null);
//                    worker0.thread.start();
                    log.debug("========> after register {}", accept.getRemoteAddress());
                }
            }
        }
    }

    static class Worker implements Runnable {
        private Thread thread;
        private Selector selector;
        private String name;
        private ConcurrentLinkedQueue<Runnable> queue = new ConcurrentLinkedQueue<>();
        private volatile boolean registered = false;

        public Worker(String name) {
            this.name = name;
        }

        public void register(SocketChannel accept) throws IOException {
            if (!this.registered) {
                this.selector = Selector.open(); //放到线程启动之前，否者run()方法中的selector将报空指针异常
                this.thread = new Thread(this, name);
                this.thread.start();
                this.registered = true;
            }
//            this.queue.add(() -> {
//                try {
//                    accept.register(this.selector, SelectionKey.OP_READ, null);
//                } catch (ClosedChannelException e) {
//                    e.printStackTrace();
//                }
//            });
            this.selector.wakeup();
            accept.register(this.selector, SelectionKey.OP_READ, null);
        }

        @Override
        public void run() {
            while (true) {
                try {
                    this.selector.select();
//                    Runnable poll = this.queue.poll();
//                    if (poll != null) {
//                        poll.run();
//                    }
                    Iterator<SelectionKey> iterator = this.selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        SelectionKey selectionKey = iterator.next();
                        iterator.remove();
                        if (selectionKey.isReadable()) {
                            SocketChannel channel = (SocketChannel) selectionKey.channel();
                            ByteBuffer buffer = ByteBuffer.allocate(16);
                            channel.read(buffer);
                            buffer.flip();
                            debugAll(buffer);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
