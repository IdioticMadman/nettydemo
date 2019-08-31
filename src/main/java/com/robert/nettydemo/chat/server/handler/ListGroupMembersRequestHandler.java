package com.robert.nettydemo.chat.server.handler;

import com.robert.nettydemo.chat.bean.Session;
import com.robert.nettydemo.chat.protocol.packet.request.ListGroupMembersRequestPacket;
import com.robert.nettydemo.chat.protocol.packet.response.ListGroupMembersResponsePacket;
import com.robert.nettydemo.chat.util.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

import java.util.ArrayList;
import java.util.List;

public class ListGroupMembersRequestHandler extends SimpleChannelInboundHandler<ListGroupMembersRequestPacket> {

    protected void channelRead0(ChannelHandlerContext ctx, ListGroupMembersRequestPacket msg) throws Exception {
        String groupId = msg.getGroupId();
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);
        List<Session> sessions = new ArrayList<>();
        if (channelGroup != null) {
            channelGroup.forEach(channel -> {
                Session session = SessionUtil.getSession(channel);
                if (session != null) {
                    sessions.add(session);
                }
            });
        }
        ListGroupMembersResponsePacket responsePacket = new ListGroupMembersResponsePacket();
        responsePacket.setGroupId(groupId);
        responsePacket.setSessions(sessions);
        ctx.channel().writeAndFlush(responsePacket);
    }
}
