package com.robert.nettydemo.chat.server.handler;

import com.robert.nettydemo.chat.protocol.Command;
import com.robert.nettydemo.chat.protocol.Packet;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.HashMap;
import java.util.Map;

public class IMHandler extends SimpleChannelInboundHandler<Packet> {

    public static final ChannelHandler INSTANCE = new IMHandler();
    private final Map<Byte, SimpleChannelInboundHandler<? extends Packet>> requestHandlers;

    protected IMHandler() {
        requestHandlers = new HashMap<>();
        requestHandlers.put(Command.CREATE_GROUP_REQUEST, CreateGroupRequestHandler.INSTANCE);
        requestHandlers.put(Command.JOIN_GROUP_REQUEST, JoinGroupRequestHandler.INSTANCE);
        requestHandlers.put(Command.QUIT_GROUP_REQUEST, QuitGroupRequestHandler.INSTANCE);
        requestHandlers.put(Command.LIST_GROUP_MEMBERS_REQUEST, ListGroupMembersRequestHandler.INSTANCE);
        requestHandlers.put(Command.MESSAGE_REQUEST, MessageRequestHandler.INSTANCE);
        requestHandlers.put(Command.GROUP_MESSAGE_REQUEST, GroupMessageRequestHandler.INSTANCE);
        requestHandlers.put(Command.LOGOUT_REQUEST, LogoutRequestHandler.INSTANCE);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet msg) throws Exception {
        SimpleChannelInboundHandler<? extends Packet> handler = requestHandlers.get(msg.getCommand());
        if (handler != null) {
            handler.channelRead(ctx, msg);
        }
    }
}
