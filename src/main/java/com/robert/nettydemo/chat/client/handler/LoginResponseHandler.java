package com.robert.nettydemo.chat.client.handler;

import com.robert.nettydemo.chat.bean.Session;
import com.robert.nettydemo.chat.client.packet.LoginResponsePacket;
import com.robert.nettydemo.chat.util.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;

public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {

    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket responsePacket) throws Exception {
        if (responsePacket.isSuccess()) {
            //标记这个channel已登录
            String userId = responsePacket.getUserId();
            String userName = responsePacket.getUserName();
            SessionUtil.bindSession(new Session(userId, userName), ctx.channel());
            System.out.println(new Date() + "：客户端登录成功");
        } else {
            System.out.println(new Date() + ": 客户端登录失败， " + responsePacket.getReason());
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        System.out.println(new Date() + "客户端链接被关闭");
    }
}
