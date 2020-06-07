package com.example.netty.file;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.util.CharsetUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author: wangdk
 * @create: 2020-05-31 17:33
 * @description: 文件传输客户端测试
 **/
public class MyFileClient {
    static int PORT = 8023;
    static String HOST = "127.0.0.1";

//    public static StringDecoder stringDecoder = new StringDecoder(CharsetUtil.UTF_8);
    public static FileClientHandler fileClientHandler = new FileClientHandler();

    static ExecutorService executorService = Executors.newSingleThreadExecutor();

    public static void main(String[] args) throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {

            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer() {

                        @Override
                        protected void initChannel(Channel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast("fileClientHandler",fileClientHandler);

                        }
                    });

            ChannelFuture f = b.connect(HOST, PORT).sync();
            Channel channel = f.channel();

            executorService.submit(new ChannelRun(channel));

            channel.closeFuture().sync();
        } finally {
            // Shut down the event loop to terminate all threads.
            group.shutdownGracefully();
            executorService.shutdown();
        }
    }
}

class ChannelRun implements Runnable {
    private Channel channel;
    private int clientId;

    public ChannelRun(Channel channel ) {
        this.channel = channel;

        System.out.println("客户端准备好了。。。。");
    }

    @Override
    public void run() {

        //注册客户端channel

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            try {

                System.out.println("请输入要发送的消息");
                String s = br.readLine()+"\n";
                ByteBuf buffer = Unpooled.copiedBuffer(s.getBytes());
                channel.writeAndFlush(buffer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}