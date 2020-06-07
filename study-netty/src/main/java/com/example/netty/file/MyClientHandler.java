package com.example.netty.file;


import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.util.UUID;

/**
 * @author: wangdk
 * @create: 2020-05-27 10:12
 * @description:
 **/
@ChannelHandler.Sharable
public class MyClientHandler extends ChannelInboundHandlerAdapter {

    private String clientId;

    private long length;
    private long offset;

    public MyClientHandler() {
        clientId = UUID.randomUUID().toString();
    }


    byte[] b = new byte[256];

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
//        msg.toString(charset)
        ByteBuf buf = (ByteBuf) msg;



    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
