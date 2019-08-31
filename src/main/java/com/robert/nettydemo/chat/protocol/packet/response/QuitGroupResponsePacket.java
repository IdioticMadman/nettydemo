package com.robert.nettydemo.chat.protocol.packet.response;

import com.robert.nettydemo.chat.protocol.Command;
import com.robert.nettydemo.chat.protocol.Packet;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@Data
public class QuitGroupResponsePacket extends Packet {

    private String groupId;
    private boolean success;
    private String reason;

    public Byte getCommand() {
        return Command.QUIT_GROUP_RESPONSE;
    }
}
