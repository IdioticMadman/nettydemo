package com.robert.nettydemo.chat.handler;

import com.robert.nettydemo.chat.bean.packet.LoginRequestPacket;
import com.robert.nettydemo.chat.bean.packet.LoginResponsePacket;
import com.robert.nettydemo.chat.codec.PacketCodeC;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket requestPacket) throws Exception {
        //构建响应
        LoginResponsePacket responsePacket = new LoginResponsePacket();
        responsePacket.setVersion(requestPacket.getVersion());
        //判断是否合法请求
        if (valid(requestPacket)) {
            responsePacket.setSuccess(true);
        } else {
            responsePacket.setSuccess(false);
            responsePacket.setReason("账号密码验证失败");
        }
        ByteBuf respBuff = PacketCodeC.INSTANCE.encode(ctx.alloc().ioBuffer(), responsePacket);
        ctx.channel().writeAndFlush(respBuff);
    }

    private boolean valid(LoginRequestPacket requestPacket) {
        return true;
    }
}
