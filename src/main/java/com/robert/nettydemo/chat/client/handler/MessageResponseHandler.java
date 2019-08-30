package com.robert.nettydemo.chat.client.handler;

import com.robert.nettydemo.chat.client.packet.MessageResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class MessageResponseHandler extends SimpleChannelInboundHandler<MessageResponsePacket> {
    protected void channelRead0(ChannelHandlerContext ctx, MessageResponsePacket responsePacket) throws Exception {
        String fromUserId = responsePacket.getFromUserId();
        String fromUserName = responsePacket.getFromUserName();
        System.out.println(fromUserId + ":" + fromUserName + " -> " + responsePacket.getMessage());

    }
}
