package org.crayzer.netty.client;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import org.crayzer.netty.codec.ObjDecoder;
import org.crayzer.netty.codec.ObjEncoder;
import org.crayzer.netty.domain.MsgInfo;

/**
 * @author <a href="mailto:yeqi@banniuyun.com">夜骐</a>
 * @since 1.0.0
 */
public class MyChannelInitializer extends ChannelInitializer {
    @Override
    protected void initChannel(Channel channel) throws Exception {
        //对象传输处理
        channel.pipeline().addLast(new ObjDecoder(MsgInfo.class));
        channel.pipeline().addLast(new ObjEncoder(MsgInfo.class));
        // 在管道中添加我们自己的接收数据实现方法
        channel.pipeline().addLast(new MyClientHandler());
    }
}
