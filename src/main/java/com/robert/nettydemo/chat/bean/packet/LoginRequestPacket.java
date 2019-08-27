package com.robert.nettydemo.chat.bean.packet;

import com.robert.nettydemo.chat.bean.Command;
import com.robert.nettydemo.chat.bean.Packet;
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
