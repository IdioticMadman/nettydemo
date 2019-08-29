package com.robert.nettydemo.chat.server.handler;

import com.robert.nettydemo.chat.protocol.packet.LoginRequestPacket;
import com.robert.nettydemo.chat.protocol.packet.LoginResponsePacket;
import com.robert.nettydemo.chat.protocol.Packet;
import com.robert.nettydemo.chat.protocol.packet.MessageRequestPacket;
import com.robert.nettydemo.chat.protocol.packet.MessageResponsePacket;
import com.robert.nettydemo.chat.protocol.codec.PacketCodeC;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;

public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;

        Packet packet = PacketCodeC.INSTANCE.decode(byteBuf);
        if (packet instanceof LoginRequestPacket) {
            LoginRequestPacket requestPacket = (LoginRequestPacket) packet;
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
        } else if (packet instanceof MessageRequestPacket) {
            MessageRequestPacket messageRequestPacket = (MessageRequestPacket) packet;
            System.out.println(new Date() + ": 收到客户端消息: " + messageRequestPacket.getMessage());

            MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
            messageResponsePacket.setMessage("服务端回复【" + messageRequestPacket.getMessage() + "】");
            ByteBuf msgRespBuf = PacketCodeC.INSTANCE.encode(ctx.alloc().ioBuffer(), messageResponsePacket);
            ctx.channel().writeAndFlush(msgRespBuf);
        }

    }

    private boolean valid(LoginRequestPacket requestPacket) {
        return true;
    }
}
