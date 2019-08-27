package com.robert.nettydemo.chat.handler;

import com.robert.nettydemo.chat.bean.packet.MessageResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;

public class MessageResponseHandler extends SimpleChannelInboundHandler<MessageResponsePacket> {
    protected void channelRead0(ChannelHandlerContext ctx, MessageResponsePacket responsePacket) throws Exception {
        System.out.println(new Date() + ": 收到服务端的消息: " + responsePacket.getMessage());
    }
}
