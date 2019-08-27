package com.robert.nettydemo.chat.handler;

import com.robert.nettydemo.chat.bean.Packet;
import com.robert.nettydemo.chat.codec.PacketCodeC;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class PacketDecoder extends ByteToMessageDecoder {

    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        Packet packet = PacketCodeC.INSTANCE.decode(in);
        out.add(packet);
    }
}
