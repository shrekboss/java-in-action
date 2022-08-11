package org.crayzer.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.crayzer.netty.util.MsgUtil;

/**
 * TODO：简单描述一嘴
 *
 * @author <a href="mailto:yeqi@banniuyun.com">夜骐</a>
 * @since 1.0.0
 */
public class NettyClient {

    public static void main(String[] args) {
        new NettyClient().connect("127.0.0.1", 7397);
    }

    private void connect(String inetHost, int inetPort) {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.AUTO_READ, true);
            b.handler(new MyChannelInitializer());
            ChannelFuture f = b.connect(inetHost, inetPort).sync();
            System.out.println("crayzer-demo-netty-2-02 client start done.");

            f.channel().writeAndFlush(MsgUtil.buildMsg(f.channel().id().toString(),"你好，使用prot使用protobuf通信格式的服务端obuf通信格式的服务端。"));
            f.channel().writeAndFlush(MsgUtil.buildMsg(f.channel().id().toString(),"你好，。"));
            f.channel().writeAndFlush(MsgUtil.buildMsg(f.channel().id().toString(),"你好，使用protobuf通信格式的服务端。"));
            f.channel().writeAndFlush(MsgUtil.buildMsg(f.channel().id().toString(),"你好，使用protobuf通信格式的服务端。"));
            f.channel().writeAndFlush(MsgUtil.buildMsg(f.channel().id().toString(),"你好，使用protobuf通信格式的服务端。"));

            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }
}
