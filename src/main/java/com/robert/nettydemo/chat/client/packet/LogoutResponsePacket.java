package com.robert.nettydemo.chat.client.packet;

import com.robert.nettydemo.chat.protocol.Command;
import com.robert.nettydemo.chat.protocol.Packet;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class LogoutResponsePacket extends Packet {

    private boolean success;
    private String reason;

    public Byte getCommand() {
        return Command.LOGOUT_RESPONSE;
    }
}
