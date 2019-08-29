package com.robert.nettydemo.chat.console.command;

import com.robert.nettydemo.chat.protocol.packet.CreateGroupRequestPacket;
import com.robert.nettydemo.chat.console.ConsoleCommand;
import io.netty.channel.Channel;

import java.util.Arrays;
import java.util.Scanner;

public class CreateGroupConsoleCommand implements ConsoleCommand {
    private static final String USER_ID_SPLITER = ",";

    public void exec(Scanner scanner, Channel channel) {

        CreateGroupRequestPacket requestPacket = new CreateGroupRequestPacket();
        System.out.println("请输入用户id，用" + USER_ID_SPLITER + "隔开");
        String userIdsStr = scanner.nextLine();
        requestPacket.setUserIds(Arrays.asList(userIdsStr.split(USER_ID_SPLITER)));
        channel.writeAndFlush(requestPacket);

    }
}
