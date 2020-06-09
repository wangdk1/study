package com.example.netty.test;

import com.example.netty.test.pojo.Student;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author: wangdk
 * @create: 2020-06-09 09:47
 * @description:
 **/
@ChannelHandler.Sharable
public class ObjectClientHandler extends SimpleChannelInboundHandler<Student> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Student msg = new Student();
        msg.setAge(12);
        msg.setName("obj-test");
        ctx.writeAndFlush(msg);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Student msg) throws Exception {
        System.out.println(msg);
        msg.setAge(msg.getAge()+1);
        if (msg.getAge()<100)
            ctx.writeAndFlush(msg);
    }
}
