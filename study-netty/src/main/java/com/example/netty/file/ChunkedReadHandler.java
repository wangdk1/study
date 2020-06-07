package com.example.netty.file;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.LineBasedFrameDecoder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * @author: wangdk
 * @create: 2020-06-03 16:08
 * @description:
 **/
//@ChannelHandler.Sharable
public class ChunkedReadHandler extends ChannelInboundHandlerAdapter {
    private long fileSize;
    private File file;
    private FileOutputStream ofs;
    private long readedSize = 0;
    private String path = "E:/test/";
    private String fileName;
    private byte[] bs;

    private boolean readFinish = false;

    public ChunkedReadHandler(long size, String fileName ,byte[] bs) {
        this.fileSize = size;
        this.fileName = fileName;
        this.bs = bs;
        this.file = new File(path + fileName);
        try {
            ofs = new FileOutputStream(this.file);
            if (this.bs.length>0){
                ofs.write(this.bs);
                readedSize += this.bs.length;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        ByteBuf buf = (ByteBuf) msg;
        do {
            long needRead = fileSize - readedSize;
            int readableBytes = buf.readableBytes();
            if (needRead >= readableBytes) {
                readedSize += readableBytes;
                byte[] bytes = new byte[readableBytes];
                buf.readBytes(bytes);
                ofs.write(bytes);

            } else if (needRead > 0) {
                readedSize += needRead;
                byte[] bytes = new byte[(int) needRead];
                buf.readBytes(bytes);
                ofs.write(bytes);
            }
            System.out.println(fileSize + "   " + readedSize);

            if (readedSize >= fileSize) {
                readFinish = true;
                ctx.pipeline().remove(this);
                addHandler(ctx);
                ofs.close();
            }/*else ctx.read();*/
        } while (buf.isReadable());

        buf.release();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        /*ctx.flush();
        System.out.println(fileSize + "   " + readedSize);
        if (!readFinish) {
            ctx.read();
        } else {
            ctx.close();
        }*/

    }

    private void addHandler(ChannelHandlerContext ctx) {
        ChannelPipeline pipeline = ctx.pipeline();
//        pipeline.addLast("lineBasedFrameDecoder", new LineBasedFrameDecoder(8192));
//        pipeline.addLast("stringDecoder", MyFileClient.stringDecoder);
        pipeline.addLast("fileClientHandler", MyFileClient.fileClientHandler);
        /*

        MyFileClient.chunkedReadHandler
        */
    }

    public void setFileName(String fileName) {
        this.file = null;
        this.ofs = null;
        this.fileName = fileName;
        this.file = new File(path + fileName);
        try {
            ofs = new FileOutputStream(this.file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public void setReadedSize(long readedSize) {
        this.readedSize = readedSize;
    }
}
