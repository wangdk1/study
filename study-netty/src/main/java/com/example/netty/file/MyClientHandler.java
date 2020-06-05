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

    /* @Override
     public void channelActive(ChannelHandlerContext ctx) {
         ByteBuf byteBuf = Unpooled.copiedBuffer("F:/spring-boot-gradle-plugin-reference.pdf".getBytes());
         ctx.writeAndFlush(byteBuf);
     }*/
    byte[] b = new byte[256];

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {

        ByteBuf buf = (ByteBuf) msg;
        String s1 = "";
        char c = buf.readChar();
//            while (c = buf.readChar())
//            OK:  ERR:  HELLO:
//
        int readableBytes = buf.readableBytes();

        buf.readBytes(b, 0, 256 > readableBytes ? readableBytes : 256);

        String s = new String(b);
        if (s.startsWith("HELLO: ")) {
            System.out.println(buf.toString(Charset.forName("utf-8")));
            return;
        }
        if (s.startsWith("OK: ")) {
//                System.out.println(buf.toString(Charset.forName("utf-8")));
            String s1222 = buf.toString(Charset.forName("utf-8"));
            length = Long.valueOf(s1.trim());
            System.out.println("文件大小：" + length);
            return;
        }
        if (s.startsWith("ERR: ")) {
            System.out.println(buf.toString(Charset.forName("utf-8")));
            return;
        }

//            F:/application.yml
//            buf.readerIndex(readerIndex);
        FileOutputStream fos = null;
        RandomAccessFile raf = null;
        try {
            fos = new FileOutputStream("E:/test/netty-file-test.yml");
            int readable = 0;
            while ((readable = buf.readableBytes()) > 0) {
                byte[] array = new byte[readable];
                buf.readBytes(array, 0, readable);
                fos.write(array);
            }
            fos.close();
//                ctx.write(new DefaultFileRegion(raf.getChannel(), 0, length));
               /* raf = new RandomAccessFile("E:/test/application.yml", "rw");
                raf.getChannel().write()*/

        } catch (Exception e) {
            e.printStackTrace();
            try {
                fos.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } finally {
            buf.release();
        }

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
