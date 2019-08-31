package com.robert.nettydemo.chat.console.command;

import com.robert.nettydemo.chat.console.ConsoleCommand;
import com.robert.nettydemo.chat.protocol.packet.request.ListGroupMembersRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

public class ListGroupMemberConsoleCommand implements ConsoleCommand {

    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.println("请输入groupId，查看群聊成员：");
        String groupId = scanner.nextLine();
        ListGroupMembersRequestPacket requestPacket = new ListGroupMembersRequestPacket();
        requestPacket.setGroupId(groupId);
        channel.writeAndFlush(requestPacket);
    }
}
