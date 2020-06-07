/*
 * Copyright 2014 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package com.example.netty.file;

import com.example.netty.file.constant.CodecType;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.DefaultFileRegion;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedFile;
import io.netty.util.CharsetUtil;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;

public class FileServerHandler extends SimpleChannelInboundHandler<String> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(CodecType.TEXT);
        String s = "连接以就绪......";
        byte[] bytes = s.getBytes();
        buf.writeShort(bytes.length);
        buf.writeBytes(bytes);
        ctx.writeAndFlush(buf);
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(CodecType.FILE);

        RandomAccessFile raf = null;
        File file = new File(msg);
        long length = -1;
        try {
            raf = new RandomAccessFile(file, "r");
            length = raf.length();

        } catch (Exception e) {
            buf.writeByte(CodecType.FILE_ERR);
            byte[] bytes = e.getMessage().getBytes();

            short writeLength = (short) bytes.length;

            buf.writeShort(writeLength);
            buf.writeBytes(bytes,0,writeLength);
            ctx.writeAndFlush(buf);
            return;
        } finally {
            if (length < 0 && raf != null) {
                raf.close();
            }
        }

        buf.writeByte(CodecType.FILE_NORMAL);
        buf.writeLong(length);
        byte[] bytes = file.getName().getBytes();
        buf.writeShort(bytes.length);//写入文件名字节数组长度
        buf.writeBytes(bytes);//文件名字节数组
        ctx.writeAndFlush(buf);
        //开始传输文件
        if (ctx.pipeline().get(SslHandler.class) == null) {
            // SSL not enabled - can use zero-copy file transfer.
            ctx.write(new DefaultFileRegion(raf.getChannel(), 0, length));
//            ctx.write(new ChunkedFile(raf));
        } else {
            // SSL enabled - cannot use zero-copy file transfer.
            ctx.write(new ChunkedFile(raf));
        }
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();

       /* if (ctx.channel().isActive()) {
            ctx.writeAndFlush("ERR: " +
                    cause.getClass().getSimpleName() + ": " +
                    cause.getMessage() + '\n').addListener(ChannelFutureListener.CLOSE);
        }*/
        if (ctx.channel().isActive()) {
            ctx.close();
        }
    }
}

