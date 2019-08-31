package com.robert.nettydemo.chat.protocol.packet.response;

import com.robert.nettydemo.chat.bean.Session;
import com.robert.nettydemo.chat.protocol.Command;
import com.robert.nettydemo.chat.protocol.Packet;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListGroupMembersResponsePacket extends Packet {
    private String groupId;
    private List<Session> sessions;

    public Byte getCommand() {
        return Command.LIST_GROUP_MEMBERS_RESPONSE;
    }
}
