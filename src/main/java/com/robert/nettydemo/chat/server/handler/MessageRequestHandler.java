package com.robert.nettydemo.chat.server.handler;

import com.robert.nettydemo.chat.bean.Session;
import com.robert.nettydemo.chat.protocol.packet.request.MessageRequestPacket;
import com.robert.nettydemo.chat.protocol.packet.response.MessageResponsePacket;
import com.robert.nettydemo.chat.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;

public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket messageRequestPacket) throws Exception {

        //获取当前channel的用户信息
        Session session = SessionUtil.getSession(ctx.channel());
        System.out.println(new Date() + ": 收到客户端消息: " + messageRequestPacket.getMessage());

        //构建消息实体
        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
        messageResponsePacket.setMessage(messageRequestPacket.getMessage());
        messageResponsePacket.setFromUserId(session.getUserId());
        messageResponsePacket.setFromUserName(session.getUserName());

        //获取需要被发送的用户的channel
        Channel toChannel = SessionUtil.getChannel(messageRequestPacket.getToUserId());
        if (toChannel != null && SessionUtil.hasLogin(toChannel)) {
            toChannel.writeAndFlush(messageResponsePacket);
        } else {
            System.err.println("[" + messageRequestPacket.getToUserId() + "] 不在线，发送失败");
        }
    }
}
