package com.example.netty.test;

import com.example.netty.test.pojo.Student;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author: wangdk
 * @create: 2020-06-09 09:52
 * @description:
 **/
public class ObjectServerHandler extends SimpleChannelInboundHandler<Student> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Student msg) throws Exception {
        System.out.println(msg);
        msg.setAge(msg.getAge()+1);
        ctx.writeAndFlush(msg);
    }
}
