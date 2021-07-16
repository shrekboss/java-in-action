package org.crayzer.conc.design.pattern.workerthread;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class EchoServer {
    public static void doEcho(int port) throws IOException {
        // ExecutorService es = Executors.newFixedThreadPool(500);
        ExecutorService es = new ThreadPoolExecutor(
                50, 500,
                60L, TimeUnit.SECONDS,
                //注意要创建有界队列
                new LinkedBlockingQueue<Runnable>(2000),
                //建议根据业务需求实现ThreadFactory
                r->{
                    return new Thread(r, "echo-"+ r.hashCode());
                },
                //建议根据业务需求实现RejectedExecutionHandler
                new ThreadPoolExecutor.CallerRunsPolicy());
        final ServerSocketChannel ssc = ServerSocketChannel.open().bind(new InetSocketAddress(port));
        try {
            while (true) {
                SocketChannel sc = ssc.accept();
                // 将请求处理任务提交给线程池
                es.execute(() -> {
                    try {
                        ByteBuffer rb = ByteBuffer.allocateDirect(1024);
                        sc.read(rb);
                        Thread.sleep(2000);
                        ByteBuffer wb = (ByteBuffer) rb.flip();
                        sc.write(wb);
                        sc.close();
                    } catch (IOException | InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        } finally {
            ssc.close();
        }
    }
}
