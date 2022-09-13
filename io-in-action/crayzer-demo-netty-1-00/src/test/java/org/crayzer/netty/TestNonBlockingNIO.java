package org.crayzer.netty;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;

/**
 * @author <a href="mailto:yeqi@banniuyun.com">夜骐</a>
 * @since 1.0.0
 */
public class TestNonBlockingNIO {

    @Test
    public void client() throws IOException {
        //1. 获取通道
        SocketChannel sChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9898));

        //2. 切换非阻塞模式
        sChannel.configureBlocking(false);

        //3. 分配指定大小的缓冲区
        ByteBuffer buf = ByteBuffer.allocate(1024);

        //4. 发送数据给服务端
        Scanner scan = new Scanner(System.in);

        while (scan.hasNext()) {
            String str = scan.next();
            buf.put((new Date() + "\n" + str).getBytes());
            buf.flip();
            sChannel.write(buf);
            buf.clear();
        }

        sChannel.close();
    }

    @Test
    public void server() throws IOException {
        //1. 获取通道
        ServerSocketChannel ssChannel = ServerSocketChannel.open();
        //2. 切换非阻塞模式
        ssChannel.configureBlocking(false);
        //3. 绑定链接
        ssChannel.bind(new InetSocketAddress(9898));
        //4. 获取选择器
        Selector selector = Selector.open();
        //5. 将通道注册到选择器上,并且指定监听事件
        ssChannel.register(selector, SelectionKey.OP_ACCEPT);
        //6. 轮训式的获取选择器上已经准备就绪的事件
        while (selector.select() > 0) {
            //7. 获取当前选择器中所有注册的选择器(已就绪的监听事件)
            Iterator<SelectionKey> it = selector.selectedKeys().iterator();
            while (it.hasNext()) {
                //8. 获取准备就绪的事件
                SelectionKey sk = it.next();
                //9. 判断具体是什么事件准备就绪
                if (sk.isAcceptable()) {
                    //10. 是获取就绪,获取客户端链接
                    SocketChannel sChannel = ssChannel.accept();
                    //11. 切换非阻塞模式
                    sChannel.configureBlocking(false);
                    //12. 将通道注册到选择器上
                    sChannel.register(selector, SelectionKey.OP_READ);
                } else if(sk.isReadable()) {
                    //13. 获取当前选择器上读就绪状态的通道
                    SocketChannel sChannel = (SocketChannel) sk.channel();
                    //14. 读取数据
                    ByteBuffer buf = ByteBuffer.allocate(1024);
                    int len;
                    while ((len = sChannel.read(buf)) > 0) {
                        buf.flip();
                        System.out.println(new String(buf.array(), 0, len));
                        buf.clear();
                    }
                }
                //15. 取消选择器 SelectionKey
                it.remove();
            }
        }
    }

    @Test(expected = ClosedChannelException.class)
    public void ClosedChannelException() throws IOException {
        Selector selector = Selector.open();
        DatagramChannel channel = DatagramChannel.open();
        channel.configureBlocking(false);
        channel.close();
        //关闭 SelectorChannel 后，注册事件，抛出 ClosedChannelException
        channel.register(selector, SelectionKey.OP_WRITE);
    }

    @Test(expected = ClosedSelectorException.class)
    public void ClosedSelectorException() throws IOException {
        DatagramChannel channel = DatagramChannel.open();
        channel.configureBlocking(false);
        Selector selector = Selector.open();
        selector.close();
        //关闭 Selector 后，注册事件，抛出 ClosedSelectorException
        channel.register(selector, SelectionKey.OP_READ);
    }

    @Test(expected = CancelledKeyException.class)
    public void CancelledKeyException() throws IOException {
        DatagramChannel channel = DatagramChannel.open();
        Selector selector = Selector.open();
        channel.configureBlocking(false);
        SelectionKey key = channel.register(selector, SelectionKey.OP_READ);
        key.cancel();
        //取消 SelectionKey 后，注册事件，抛出 CancelledKeyException
        //selector.selectNow(); // 如果刷新一下就不会抛出异常
        channel.register(selector, SelectionKey.OP_WRITE);
    }
}
