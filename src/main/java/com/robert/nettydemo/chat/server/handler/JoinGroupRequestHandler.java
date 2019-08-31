package com.robert.nettydemo.chat.server.handler;

import com.robert.nettydemo.chat.protocol.packet.request.JoinGroupRequestPacket;
import com.robert.nettydemo.chat.protocol.packet.response.JoinGroupResponsePacket;
import com.robert.nettydemo.chat.util.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

import java.util.Date;
@ChannelHandler.Sharable
public class JoinGroupRequestHandler extends SimpleChannelInboundHandler<JoinGroupRequestPacket> {
    public static final JoinGroupRequestHandler INSTANCE = new JoinGroupRequestHandler();

    protected JoinGroupRequestHandler() {
    }
    protected void channelRead0(ChannelHandlerContext ctx, JoinGroupRequestPacket msg) throws Exception {
        String groupId = msg.getGroupId();
        ChannelGroup channelGroup =
                SessionUtil.getChannelGroup(groupId);
        if (channelGroup != null) {
            channelGroup.add(ctx.channel());
            JoinGroupResponsePacket responsePacket = new JoinGroupResponsePacket();
            responsePacket.setSuccess(true);
            responsePacket.setGroupId(groupId);
            ctx.channel().writeAndFlush(responsePacket);
        } else {
            System.out.println(new Date() + ": 群聊不存在");
        }

    }
}
