package com.robert.nettydemo.chat.handler;

import com.robert.nettydemo.chat.bean.packet.MessageRequestPacket;
import com.robert.nettydemo.chat.bean.packet.MessageResponsePacket;
import com.robert.nettydemo.chat.codec.PacketCodeC;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;

public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket messageRequestPacket) throws Exception {

        System.out.println(new Date() + ": 收到客户端消息: " + messageRequestPacket.getMessage());
        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
        messageResponsePacket.setMessage("服务端回复【" + messageRequestPacket.getMessage() + "】");
        ByteBuf msgRespBuf = PacketCodeC.INSTANCE.encode(ctx.alloc().ioBuffer(), messageResponsePacket);
        ctx.channel().writeAndFlush(msgRespBuf);
    }
}
