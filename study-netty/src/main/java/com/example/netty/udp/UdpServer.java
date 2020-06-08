package com.example.netty.udp;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

/**
 * @author: wangdk
 * @create: 2020-06-08 15:44
 * @description:
 **/
public class UdpServer {
    static int port = 8088;

    public static void main(String[] args) throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        //由于我们用的是UDP协议，所以要用NioDatagramChannel来创建
        b.group(group).channel(NioDatagramChannel.class)
                .option(ChannelOption.SO_BROADCAST, true)//支持广播
                .handler(new UdpServerHandler());
        Channel channel = b.bind(port).sync().channel();
        channel.closeFuture().await();
    }
}
