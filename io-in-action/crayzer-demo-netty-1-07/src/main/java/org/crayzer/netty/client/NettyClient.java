package org.crayzer.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * NettyClient
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
            System.out.println("crayzer-demo-netty-07 client start done. ");
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }
}
