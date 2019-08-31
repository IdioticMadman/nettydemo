package com.robert.nettydemo.chat.server.handler;

import com.robert.nettydemo.chat.protocol.packet.request.QuitGroupRequestPacket;
import com.robert.nettydemo.chat.protocol.packet.response.QuitGroupResponsePacket;
import com.robert.nettydemo.chat.util.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

public class QuitGroupRequestHandler extends SimpleChannelInboundHandler<QuitGroupRequestPacket> {

    protected void channelRead0(ChannelHandlerContext ctx, QuitGroupRequestPacket msg) throws Exception {
        String groupId = msg.getGroupId();
        ChannelGroup channelGroup =
                SessionUtil.getChannelGroup(groupId);
        if (channelGroup != null) {
            channelGroup.remove(ctx.channel());
        }

        QuitGroupResponsePacket responsePacket = new QuitGroupResponsePacket();
        responsePacket.setGroupId(groupId);
        responsePacket.setSuccess(true);

        ctx.channel().writeAndFlush(responsePacket);
    }
}
