package com.robert.nettydemo.chat.protocol.codec;

import com.robert.nettydemo.chat.protocol.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.Date;
import java.util.List;

public class PacketDecoder extends ByteToMessageDecoder {

    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        Packet packet = PacketCodeC.INSTANCE.decode(in);
        System.out.println(new Date() + ": 收到信息" + packet.toString());
        out.add(packet);
    }
}
