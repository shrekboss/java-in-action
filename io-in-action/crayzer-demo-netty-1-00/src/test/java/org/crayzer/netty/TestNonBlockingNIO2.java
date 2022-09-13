package org.crayzer.netty;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;

/**
 * @author <a href="mailto:yeqi@banniuyun.com">夜骐</a>
 * @since 1.0.0
 */
public class TestNonBlockingNIO2 {

    @Test
    public void send() throws IOException {
        DatagramChannel dc = DatagramChannel.open();
        dc.configureBlocking(false);
        ByteBuffer buf = ByteBuffer.allocate(1024);

        Scanner scan = new Scanner(System.in);
        while (scan.hasNext()) {
            String str = scan.next();
            buf.put((new Date() + "\n" + str).getBytes());
            buf.flip();
            dc.send(buf, new InetSocketAddress("127.0.0.1", 9898));
            buf.clear();
        }
        dc.close();
    }

    @Test
    public void receive() throws IOException {
        //1. 获取通道
        DatagramChannel dc = DatagramChannel.open();
        //2. 切换非阻塞模式
        dc.configureBlocking(false);
        //3. 绑定链接
        dc.bind(new InetSocketAddress(9898));
        //4. 获取选择器
        Selector selector = Selector.open();
        //5. 将通道注册到选择器上,并且指定监听事件
        dc.register(selector, SelectionKey.OP_READ);
        //6. 轮训式的获取选择器上已经准备就绪的事件
        while (selector.select() > 0) {
            //7. 获取当前选择器中所有注册的选择器(已就绪的监听事件)
            Iterator<SelectionKey> it = selector.selectedKeys().iterator();
            while (it.hasNext()) {
                //8. 获取准备就绪的事件
                SelectionKey sk = it.next();
                //9. 判断具体是什么事件准备就绪
                if (sk.isReadable()) {
                    ByteBuffer buf = ByteBuffer.allocate(1024);
                    dc.receive(buf);
                    buf.flip();
                    System.out.println(new String(buf.array(), 0, buf.limit()));
                }
                //15. 取消选择器 SelectionKey
                it.remove();
            }
        }
    }
}
