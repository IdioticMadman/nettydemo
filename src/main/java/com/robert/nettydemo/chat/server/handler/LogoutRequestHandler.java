package com.robert.nettydemo.chat.server.handler;

import com.robert.nettydemo.chat.server.packet.LogoutRequestPacket;
import com.robert.nettydemo.chat.client.packet.LogoutResponsePacket;
import com.robert.nettydemo.chat.util.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class LogoutRequestHandler extends SimpleChannelInboundHandler<LogoutRequestPacket> {

    protected void channelRead0(ChannelHandlerContext ctx, LogoutRequestPacket msg) throws Exception {
        SessionUtil.unBindSession(ctx.channel());
        LogoutResponsePacket responsePacket = new LogoutResponsePacket();
        responsePacket.setSuccess(true);
        ctx.channel().writeAndFlush(responsePacket);
    }
}
