package com.example.netty.file;

import com.example.netty.file.constant.CodecType;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;

/**
 * @author: wangdk
 * @create: 2020-05-31 18:09
 * @description:
 **/
@ChannelHandler.Sharable
public class FileClientHandler extends ChannelInboundHandlerAdapter {
    static final String KEY = "OK: ";
    Charset charset = CharsetUtil.UTF_8;
//    long length;
/*  SimpleChannelInboundHandler<String>
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
*/

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf buf = (ByteBuf) msg;

        short strLength = 0;
        byte[] bs = null;
        int flag = buf.readInt();
        if (flag == CodecType.TEXT){
            strLength = buf.readShort();
            bs = new byte[strLength];

            buf.readBytes(bs);
            System.out.println(new String(bs));


        }else if (flag == CodecType.FILE){
            byte result = buf.readByte();
            if (result == CodecType.FILE_ERR){
                strLength = buf.readShort();
                bs = new byte[strLength];

                buf.readBytes(bs);
                System.out.println(new String(bs));

            }else if (result == CodecType.FILE_NORMAL){

                long fileLength = buf.readLong();
                strLength = buf.readShort();
                bs = new byte[strLength];
                buf.readBytes(bs);
                String fileName = new String(bs);


                int remain = buf.readableBytes();
                bs = new byte[remain];
                buf.readBytes(bs);
                removeHandler(ctx);
                addHandler(ctx,fileLength,fileName,bs);

            }

        }

        System.out.println("----------------");
       /* String s = buf.toString(charset);
        if (s.startsWith(KEY)) {
            String trim = s.replace(KEY, "").trim();
            String[] split = trim.split(";");
            length = Long.valueOf(split[0]);
            System.out.println("length:"+length);
            removeHandler(ctx);
            String fileName =split[1];
            addHandler(ctx,length,fileName);
        }*/


    }


    private void addHandler(ChannelHandlerContext ctx, Long length, String fileName,byte[] bs) {
        ChannelPipeline pipeline = ctx.pipeline();
        ChunkedReadHandler chunkedReadHandler = new ChunkedReadHandler(length, fileName,bs);

//        chunkedReadHandler.setReadedSize(0l);
        pipeline.addFirst("chunkedReadHandler", chunkedReadHandler);

    }

    private void removeHandler(ChannelHandlerContext ctx) {
        ChannelPipeline pipeline = ctx.pipeline();
//        pipeline.remove("lineBasedFrameDecoder");
//        pipeline.remove("stringDecoder");
        pipeline.remove("fileClientHandler");
    }

}
