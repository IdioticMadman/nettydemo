package com.robert.nettydemo.chat.protocol.packet.response;

import com.robert.nettydemo.chat.protocol.Command;
import com.robert.nettydemo.chat.protocol.Packet;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class MessageResponsePacket extends Packet {

    private String message;
    private String fromUserId;
    private String fromUserName;

    public Byte getCommand() {
        return Command.MESSAGE_RESPONSE;
    }
}
