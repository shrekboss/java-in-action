package org.crayzer.netty.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import org.crayzer.netty.codec.MyDecoder;
import org.crayzer.netty.codec.MyEncoder;


/**
 * @author <a href="mailto:yeqi@banniuyun.com">夜骐</a>
 * @since 1.0.0
 */
public class MyChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        //自定义解码器
        ch.pipeline().addLast(new MyDecoder());
        //自定义编码器
        ch.pipeline().addLast(new MyEncoder());
        //在管道中添加我们自己的接收数据实现方法
        ch.pipeline().addLast(new MyServerHandler());
    }
}
