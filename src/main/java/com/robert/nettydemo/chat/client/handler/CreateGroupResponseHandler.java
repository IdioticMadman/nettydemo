package com.robert.nettydemo.chat.client.handler;

import com.robert.nettydemo.chat.protocol.packet.response.CreateGroupResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;

public class CreateGroupResponseHandler extends SimpleChannelInboundHandler<CreateGroupResponsePacket> {

    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupResponsePacket responsePacket) throws Exception {
        String log = new Date() + ": 群创建成功，id为[ " + responsePacket.getGroupId() + " ], 群里面有： "
                + responsePacket.getUserNames();
        System.out.println(log);
    }
}
