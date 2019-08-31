package com.robert.nettydemo.chat.server.handler;

import com.robert.nettydemo.chat.protocol.packet.request.GroupMessageRequestPacket;
import com.robert.nettydemo.chat.protocol.packet.response.GroupMessageResponsePacket;
import com.robert.nettydemo.chat.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

@ChannelHandler.Sharable
public class GroupMessageRequestHandler extends SimpleChannelInboundHandler<GroupMessageRequestPacket> {

    public static final GroupMessageRequestHandler INSTANCE = new GroupMessageRequestHandler();

    protected GroupMessageRequestHandler() {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupMessageRequestPacket msg) throws Exception {
        String toGroupId = msg.getToGroupId();
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(toGroupId);
        Channel channel = ctx.channel();
        if (channelGroup != null) {
            GroupMessageResponsePacket responsePacket = new GroupMessageResponsePacket();
            responsePacket.setSuccess(true);
            responsePacket.setFromGroupId(toGroupId);
            responsePacket.setContent(msg.getContent());
            responsePacket.setFromUser(SessionUtil.getSession(channel));
            channelGroup.writeAndFlush(responsePacket);
        }
    }
}
