package com.robert.nettydemo.pipelinedemo;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

import java.util.Date;

public class ChannelOutBoundC extends ChannelOutboundHandlerAdapter {

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        System.out.println(new Date() + ": " + ChannelOutBoundC.class.getSimpleName());
        super.write(ctx, msg, promise);
    }
}
