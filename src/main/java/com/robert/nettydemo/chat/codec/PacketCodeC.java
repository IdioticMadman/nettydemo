package com.robert.nettydemo.chat.codec;

import com.robert.nettydemo.chat.bean.Command;
import com.robert.nettydemo.chat.bean.packet.LoginRequestPacket;
import com.robert.nettydemo.chat.bean.packet.LoginResponsePacket;
import com.robert.nettydemo.chat.bean.Packet;
import com.robert.nettydemo.chat.bean.packet.MessageRequestPacket;
import com.robert.nettydemo.chat.bean.packet.MessageResponsePacket;
import com.robert.nettydemo.chat.serializer.JsonSerializer;
import com.robert.nettydemo.chat.serializer.SerializeAlgorithm;
import com.robert.nettydemo.chat.serializer.Serializer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

import java.util.HashMap;
import java.util.Map;

public class PacketCodeC {
    public static final int MAGIC_NUMBER = 0x12345678;

    private static final Map<Byte, Class<? extends Packet>> packetTypeMap;
    private static final Map<Byte, Serializer> serializerMap;

    public static final PacketCodeC INSTANCE = new PacketCodeC();

    static {
        packetTypeMap = new HashMap<Byte, Class<? extends Packet>>();
        packetTypeMap.put(Command.LOGIN_REQUEST, LoginRequestPacket.class);
        packetTypeMap.put(Command.LOGIN_RESPONSE, LoginResponsePacket.class);
        packetTypeMap.put(Command.MESSAGE_REQUEST, MessageRequestPacket.class);
        packetTypeMap.put(Command.MESSAGE_RESPONSE, MessageResponsePacket.class);

        serializerMap = new HashMap<Byte, Serializer>();
        Serializer serializer = new JsonSerializer();
        serializerMap.put(SerializeAlgorithm.JSON, serializer);
    }

    public ByteBuf encode(ByteBuf byteBuf, Packet packet) {
        //序列化
        byte[] data = Serializer.DEFAULT.serialize(packet);
        //编码过程
        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlgorithm());
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(data.length);
        byteBuf.writeBytes(data);
        return byteBuf;
    }

    public Packet decode(ByteBuf byteBuf) {
        //跳过magicNumber
        byteBuf.skipBytes(4);
        //跳过版本号
        byteBuf.skipBytes(1);
        //序列化标识
        byte serializeAlgorithm = byteBuf.readByte();
        //命令字
        byte command = byteBuf.readByte();
        //包体长度
        int length = byteBuf.readInt();
        byte[] data = new byte[length];
        byteBuf.readBytes(data);

        Class<? extends Packet> requestType = getRequestType(command);
        Serializer serializer = getSerializer(serializeAlgorithm);
        if (requestType != null && serializer != null) {
            return serializer.deserialize(requestType, data);
        }
        return null;
    }

    private Serializer getSerializer(byte serializeAlgorithm) {
        return serializerMap.get(serializeAlgorithm);
    }

    private Class<? extends Packet> getRequestType(byte command) {
        return packetTypeMap.get(command);
    }
}
