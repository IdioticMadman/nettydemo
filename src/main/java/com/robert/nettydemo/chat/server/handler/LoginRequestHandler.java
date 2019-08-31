package com.robert.nettydemo.chat.server.handler;

import com.robert.nettydemo.chat.bean.Session;
import com.robert.nettydemo.chat.protocol.packet.request.LoginRequestPacket;
import com.robert.nettydemo.chat.protocol.packet.response.LoginResponsePacket;
import com.robert.nettydemo.chat.util.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;

@ChannelHandler.Sharable
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {

    public static final LoginRequestHandler INSTANCE = new LoginRequestHandler();

    protected LoginRequestHandler() {
    }

    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket requestPacket) throws Exception {
        //构建响应
        LoginResponsePacket responsePacket = new LoginResponsePacket();
        responsePacket.setVersion(requestPacket.getVersion());
        //判断是否合法请求
        if (valid(requestPacket)) {
            Session session = new Session();
            String uuid = requestPacket.getUuid();
            String username = requestPacket.getUsername();
            session.setUserId(uuid);
            session.setUserName(username);
            //保存userId对应的channel，后续通过userId找到Channel发送
            SessionUtil.bindSession(session, ctx.channel());

            responsePacket.setSuccess(true);
            responsePacket.setUserId(uuid);
            responsePacket.setUserName(username);
            System.out.println(new Date() + ": 用户 " + username + "登录成功！");
        } else {
            responsePacket.setSuccess(false);
            responsePacket.setReason("账号密码验证失败");
        }
        ctx.channel().writeAndFlush(responsePacket);
    }

    private boolean valid(LoginRequestPacket requestPacket) {
        return true;
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        //链接被断开，需要清空
        SessionUtil.unBindSession(ctx.channel());
    }
}
