package com.robert.nettydemo.chat.protocol.packet;

import com.robert.nettydemo.chat.protocol.Command;
import com.robert.nettydemo.chat.protocol.Packet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class MessageRequestPacket extends Packet {
    private String toUserId;
    private String message;

    public Byte getCommand() {
        return Command.MESSAGE_REQUEST;
    }
}
