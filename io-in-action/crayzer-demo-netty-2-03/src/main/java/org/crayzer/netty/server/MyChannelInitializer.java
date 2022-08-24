package org.crayzer.netty.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import org.crayzer.netty.codec.ObjDecoder;
import org.crayzer.netty.codec.ObjEncoder;
import org.crayzer.netty.domain.MsgInfo;

/**
 * @author <a href="mailto:yeqi@banniuyun.com">夜骐</a>
 * @since 1.0.0
 */
public class MyChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        //对象传输处理
        ch.pipeline().addLast(new ObjDecoder(MsgInfo.class));
        ch.pipeline().addLast(new ObjEncoder(MsgInfo.class));
        // 在管道中添加我们自己的接收数据实现方法
        ch.pipeline().addLast(new MyServerHandler());
    }
}
