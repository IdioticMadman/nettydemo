package com.robert.nettydemo.chat.bean.packet;

import com.robert.nettydemo.chat.bean.Command;
import com.robert.nettydemo.chat.bean.Packet;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class LoginResponsePacket extends Packet {

    private boolean success;
    private String reason;

    public Byte getCommand() {
        return Command.LOGIN_RESPONSE;
    }
}
