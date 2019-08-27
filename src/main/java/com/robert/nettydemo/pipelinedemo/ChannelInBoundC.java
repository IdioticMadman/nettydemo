package com.robert.nettydemo.pipelinedemo;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;

public class ChannelInBoundC extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println(new Date() + ": " + ChannelInBoundC.class.getSimpleName());
        ctx.channel().writeAndFlush(msg);
    }
}
