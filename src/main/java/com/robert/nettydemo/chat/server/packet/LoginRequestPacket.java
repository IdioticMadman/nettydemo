package com.robert.nettydemo.chat.server.packet;

import com.robert.nettydemo.chat.protocol.Command;
import com.robert.nettydemo.chat.protocol.Packet;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class LoginRequestPacket extends Packet {

    private String uuid;
    private String username;
    private String password;

    public Byte getCommand() {
        return Command.LOGIN_REQUEST;
    }
}
