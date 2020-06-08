package com.example.netty.test;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author: wangdk
 * @create: 2020-06-08 13:40
 * @description:
 **/
public class Test1Client {

    static int PORT = 8992;
    static String HOST = "127.0.0.1";

    static ExecutorService executorService = Executors.newCachedThreadPool();

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
                            pipeline.addLast(new LineBasedFrameDecoder(8192));
                            pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
                            pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));
                            pipeline.addLast(new Test1ClientHandler());

                        }
                    });

            ChannelFuture f = b.connect(HOST, PORT).sync();
            Channel channel = f.channel();

            executorService.submit(() -> {
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                while (true) {
                    try {

                        System.out.println("请输入要发送的消息");
                        String s = br.readLine() + "\n";
                        ByteBuf buffer = Unpooled.copiedBuffer(s.getBytes());
//                        channel.writeAndFlush(buffer);
                        channel.writeAndFlush(s);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

           /* executorService.submit(() -> {
                int i = 0;
                while (true) {

                    String s = "自动发送: " + i + "\n";
                    ByteBuf buffer = Unpooled.copiedBuffer(s.getBytes());
                    channel.writeAndFlush(buffer);
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(i++>100)
                        break;
                }
            });*/

            channel.closeFuture().sync();
        } finally {
            // Shut down the event loop to terminate all threads.
            group.shutdownGracefully();
            executorService.shutdown();
        }
    }
}
