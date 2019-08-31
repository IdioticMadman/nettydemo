package com.robert.nettydemo.chat.client.handler;

import com.robert.nettydemo.chat.protocol.packet.response.LogoutResponsePacket;
import com.robert.nettydemo.chat.util.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;

public class LogoutResponseHandler extends SimpleChannelInboundHandler<LogoutResponsePacket> {

    protected void channelRead0(ChannelHandlerContext ctx, LogoutResponsePacket msg) throws Exception {
        SessionUtil.unBindSession(ctx.channel());
        System.out.println(new Date() + "客户端已退出");
    }
}
