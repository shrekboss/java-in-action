package org.crayzer.netty.cient;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.nio.NioDatagramChannel;


/**
 * @author <a href="mailto:yeqi@banniuyun.com">夜骐</a>
 * @since 1.0.0
 */
public class MyChannelInitializer extends ChannelInitializer<NioDatagramChannel> {
    @Override
    protected void initChannel(NioDatagramChannel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        // 解码转String，注意调整自己的编码格式GBK、UTF-8
        //pipeline.addLast("stringDecoder", new StringDecoder(Charset.forName("GBK")));
        pipeline.addLast(new MyClientHandler());
    }
}
