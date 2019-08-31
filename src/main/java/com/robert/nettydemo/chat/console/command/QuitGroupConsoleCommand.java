package com.robert.nettydemo.chat.console.command;

import com.robert.nettydemo.chat.console.ConsoleCommand;
import com.robert.nettydemo.chat.protocol.packet.request.JoinGroupRequestPacket;
import com.robert.nettydemo.chat.protocol.packet.request.QuitGroupRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

public class QuitGroupConsoleCommand implements ConsoleCommand {
    public void exec(Scanner scanner, Channel channel) {

        System.out.println("请输入groupId，退出群聊：");
        String groupId = scanner.nextLine();
        QuitGroupRequestPacket requestPacket = new QuitGroupRequestPacket();
        requestPacket.setGroupId(groupId);
        channel.writeAndFlush(requestPacket);
    }
}
