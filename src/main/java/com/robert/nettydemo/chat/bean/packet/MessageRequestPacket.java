package com.robert.nettydemo.chat.bean.packet;

import com.robert.nettydemo.chat.bean.Command;
import com.robert.nettydemo.chat.bean.Packet;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class MessageRequestPacket extends Packet {

    private String message;

    public Byte getCommand() {
        return Command.MESSAGE_REQUEST;
    }
}
