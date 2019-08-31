package com.robert.nettydemo.chat.console.command;

import com.robert.nettydemo.chat.console.ConsoleCommand;
import com.robert.nettydemo.chat.protocol.packet.request.JoinGroupRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

public class JoinGroupConsoleCommand implements ConsoleCommand {
    public void exec(Scanner scanner, Channel channel) {

        System.out.println("请输入groupId，加入群聊：");
        String groupId = scanner.nextLine();
        JoinGroupRequestPacket requestPacket = new JoinGroupRequestPacket();
        requestPacket.setGroupId(groupId);
        channel.writeAndFlush(requestPacket);
    }
}
