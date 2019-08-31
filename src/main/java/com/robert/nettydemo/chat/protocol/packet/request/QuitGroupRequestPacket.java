package com.robert.nettydemo.chat.protocol.packet.request;

import com.robert.nettydemo.chat.protocol.Command;
import com.robert.nettydemo.chat.protocol.Packet;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class QuitGroupRequestPacket extends Packet {

    private String groupId;


    public Byte getCommand() {
        return Command.QUIT_GROUP_REQUEST;
    }
}
