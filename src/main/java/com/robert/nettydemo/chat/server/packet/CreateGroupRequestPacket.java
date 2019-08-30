package com.robert.nettydemo.chat.server.packet;

import com.robert.nettydemo.chat.protocol.Command;
import com.robert.nettydemo.chat.protocol.Packet;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class CreateGroupRequestPacket extends Packet {
    private List<String> userIds;

    public Byte getCommand() {
        return Command.CREATE_GROUP_REQUEST;
    }
}
