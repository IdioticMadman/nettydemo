package com.robert.nettydemo.chat.protocol.packet.request;

import com.robert.nettydemo.chat.protocol.Command;
import com.robert.nettydemo.chat.protocol.Packet;
import com.sun.xml.internal.bind.v2.runtime.reflect.Lister;

public class LogoutRequestPacket extends Packet {
    public Byte getCommand() {
        return Command.LOGOUT_REQUEST;
    }
}
