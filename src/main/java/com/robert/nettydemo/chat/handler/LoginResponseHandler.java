package com.robert.nettydemo.chat.handler;

import com.robert.nettydemo.chat.bean.packet.LoginRequestPacket;
import com.robert.nettydemo.chat.bean.packet.LoginResponsePacket;
import com.robert.nettydemo.chat.codec.PacketCodeC;
import com.robert.nettydemo.chat.util.LoginUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;
import java.util.UUID;

public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(new Date() + ": 客户端开始登录");

        LoginRequestPacket packet = new LoginRequestPacket();
        packet.setUuid(UUID.randomUUID().toString());
        packet.setUsername("robert");
        packet.setPassword("123456");

        ctx.channel().writeAndFlush(packet);
    }

    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket responsePacket) throws Exception {
        if (responsePacket.isSuccess()) {
            //标记这个channel已登录
            LoginUtil.markLogin(ctx.channel());
            System.out.println(new Date() + "：客户端登录成功");
        } else {
            System.out.println(new Date() + ": 客户端登录失败， " + responsePacket.getReason());
        }
    }
}
