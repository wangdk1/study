package com.example.netty.udp;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;

/**
 * @author: wangdk
 * @create: 2020-06-08 15:44
 * @description:
 **/
public class UDPClient {
    static int port = 8088;
    public static void main(String[] args) {
        EventLoopGroup group  = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioDatagramChannel.class)
                    .option(ChannelOption.SO_BROADCAST,true)//允许广播
                    .handler(new UdpClientHandler());//设置消息处理器
            Channel ch = b.bind(0).sync().channel();
            //向网段内的所有机器广播UDP消息。
            ch.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer("udp广播测试！",
                    CharsetUtil.UTF_8), new InetSocketAddress("255.255.255.255",port))).sync();
            if(!ch.closeFuture().await(15000)){
                System.out.println("查询超时！");
            }
        } catch (Exception e) {
            group.shutdownGracefully();
        }
    }
}
