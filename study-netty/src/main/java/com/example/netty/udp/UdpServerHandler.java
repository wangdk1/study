package com.example.netty.udp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;

/**
 * @author: wangdk
 * @create: 2020-06-08 16:05
 * @description:
 **/
public class UdpServerHandler extends SimpleChannelInboundHandler<DatagramPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket packet) throws Exception {
        ByteBuf content = packet.content();
        String s = content.toString(CharsetUtil.UTF_8);
        System.out.println(s);

        InetSocketAddress sender = packet.sender();
        DatagramPacket packet2 = new DatagramPacket(Unpooled.copiedBuffer("服务器回送数据！",
                CharsetUtil.UTF_8),sender);
        ctx.writeAndFlush(packet2);
//        packet.release();
    }
}
