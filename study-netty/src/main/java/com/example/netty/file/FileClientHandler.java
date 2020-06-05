package com.example.netty.file;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author: wangdk
 * @create: 2020-05-31 18:09
 * @description:
 **/
@ChannelHandler.Sharable
public class FileClientHandler extends SimpleChannelInboundHandler<String> {
    static final String KEY = "OK: ";
    long length;
    @Override
    public void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println(msg);
        if (msg.startsWith(KEY)) {
            String trim = msg.replace(KEY, "").trim();
            String[] split = trim.split(";");
            length = Long.valueOf(split[0]);
            System.out.println("length:"+length);
            removeHandler(ctx);
            String fileName =split[1];
            addHandler(ctx,length,fileName);
        }
    }

    private void addHandler(ChannelHandlerContext ctx, Long length, String fileName) {
        ChannelPipeline pipeline = ctx.pipeline();
        ChunkedReadHandler chunkedReadHandler = new ChunkedReadHandler(length,fileName);;
        chunkedReadHandler.setReadedSize(0l);
        pipeline.addFirst("chunkedReadHandler",chunkedReadHandler);

    }

    private void removeHandler(ChannelHandlerContext ctx) {
        ChannelPipeline pipeline = ctx.pipeline();
        pipeline.remove("fileClientHandler");
        pipeline.remove("stringDecoder");
        pipeline.remove("lineBasedFrameDecoder");
    }

}
