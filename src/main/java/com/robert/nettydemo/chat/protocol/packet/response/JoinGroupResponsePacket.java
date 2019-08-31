package com.robert.nettydemo.chat.protocol.packet.response;

import com.robert.nettydemo.chat.protocol.Command;
import com.robert.nettydemo.chat.protocol.Packet;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class JoinGroupResponsePacket extends Packet {
    private boolean success;
    private String groupId;
    private String reason;

    public Byte getCommand() {
        return Command.JOIN_GROUP_RESPONSE;
    }
}
