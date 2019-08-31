package com.robert.nettydemo.chat.protocol.packet.response;

import com.robert.nettydemo.chat.protocol.Command;
import com.robert.nettydemo.chat.protocol.Packet;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class CreateGroupResponsePacket extends Packet {

    private List<String> userNames;
    private boolean success;
    private String groupId;

    public Byte getCommand() {
        return Command.CREATE_GROUP_RESPONSE;
    }
}
