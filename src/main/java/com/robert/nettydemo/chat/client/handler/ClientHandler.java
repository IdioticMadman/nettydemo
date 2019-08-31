package com.robert.nettydemo.chat.client.handler;

import com.robert.nettydemo.chat.protocol.packet.request.LoginRequestPacket;
import com.robert.nettydemo.chat.protocol.packet.response.LoginResponsePacket;
import com.robert.nettydemo.chat.protocol.Packet;
import com.robert.nettydemo.chat.protocol.packet.response.MessageResponsePacket;
import com.robert.nettydemo.chat.protocol.codec.PacketCodeC;
import com.robert.nettydemo.chat.util.LoginUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;
import java.util.UUID;

public class ClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(new Date() + ": 客户端开始登录");

        LoginRequestPacket packet = new LoginRequestPacket();
        packet.setUuid(UUID.randomUUID().toString());
        packet.setUsername("robert");
        packet.setPassword("123456");

        ByteBuf byteBuf = PacketCodeC.INSTANCE.encode(ctx.alloc().ioBuffer(), packet);

        ctx.channel().writeAndFlush(byteBuf);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        Packet packet = PacketCodeC.INSTANCE.decode(byteBuf);
        if (packet instanceof LoginResponsePacket) {
            LoginResponsePacket responsePacket = (LoginResponsePacket) packet;
            if (responsePacket.isSuccess()) {
                //标记这个channel已登录
                LoginUtil.markLogin(ctx.channel());
                System.out.println(new Date() + "：客户端登录成功");
            } else {
                System.out.println(new Date() + ": 客户端登录失败， " + responsePacket.getReason());
            }
        } else if (packet instanceof MessageResponsePacket) {
            MessageResponsePacket msgRespPacket = (MessageResponsePacket) packet;
            System.out.println(new Date() + ": 收到服务端的消息: " + msgRespPacket.getMessage());
        }
    }
}
