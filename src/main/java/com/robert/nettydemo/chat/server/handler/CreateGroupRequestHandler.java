package com.robert.nettydemo.chat.server.handler;

import com.robert.nettydemo.chat.bean.Session;
import com.robert.nettydemo.chat.protocol.packet.response.CreateGroupResponsePacket;
import com.robert.nettydemo.chat.protocol.packet.request.CreateGroupRequestPacket;
import com.robert.nettydemo.chat.util.IdUtil;
import com.robert.nettydemo.chat.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CreateGroupRequestHandler extends SimpleChannelInboundHandler<CreateGroupRequestPacket> {

    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupRequestPacket msg) throws Exception {
        List<String> userIds = msg.getUserIds();
        List<String> userNames = new ArrayList<String>();
        //创建channelGroup
        ChannelGroup group = new DefaultChannelGroup(ctx.executor());

        //找到对应的channel和userId
        for (String userId : userIds) {
            Channel channel = SessionUtil.getChannel(userId);
            if (channel != null) {
                group.add(channel);
                userNames.add(SessionUtil.getSession(channel).getUserName());
            }
        }
        //创建响应
        CreateGroupResponsePacket responsePacket = new CreateGroupResponsePacket();
        responsePacket.setGroupId(IdUtil.randomId());
        responsePacket.setUserNames(userNames);
        responsePacket.setSuccess(true);

        //保存ChannelGroup
        SessionUtil.bindChannelGroup(responsePacket.getGroupId(), group);

        //给每个客户端回应
        group.writeAndFlush(responsePacket);

        String log = new Date() + ": 群创建成功，id为[ " + responsePacket.getGroupId() + " ], 群里面有： "
                + responsePacket.getUserNames();
        System.out.println(log);
    }
}
