package com.example.netty.test;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author: wangdk
 * @create: 2020-06-08 13:37
 * @description:
 **/
@ChannelHandler.Sharable
public class Test1ServerHandler extends SimpleChannelInboundHandler<String> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println(msg);
        ctx.writeAndFlush("服务端回送："+msg+"\n");
    }
}
