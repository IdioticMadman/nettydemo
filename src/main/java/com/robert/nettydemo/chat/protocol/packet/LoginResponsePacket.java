package com.robert.nettydemo.chat.protocol.packet;

import com.robert.nettydemo.chat.protocol.Command;
import com.robert.nettydemo.chat.protocol.Packet;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class LoginResponsePacket extends Packet {

    private boolean success;
    private String reason;
    private String userId;
    private String userName;

    public Byte getCommand() {
        return Command.LOGIN_RESPONSE;
    }
}
