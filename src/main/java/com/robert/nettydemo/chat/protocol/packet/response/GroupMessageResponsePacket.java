package com.robert.nettydemo.chat.protocol.packet.response;

import com.robert.nettydemo.chat.bean.Session;
import com.robert.nettydemo.chat.protocol.Command;
import com.robert.nettydemo.chat.protocol.Packet;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class GroupMessageResponsePacket extends Packet {

    private boolean success;
    private String reason;
    private String fromGroupId;
    private String content;
    private Session fromUser;

    @Override
    public Byte getCommand() {
        return Command.GROUP_MESSAGE_RESPONSE;
    }
}
