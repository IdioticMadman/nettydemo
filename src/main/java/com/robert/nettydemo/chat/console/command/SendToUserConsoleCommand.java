package com.robert.nettydemo.chat.console.command;

import com.robert.nettydemo.chat.server.packet.MessageRequestPacket;
import com.robert.nettydemo.chat.console.ConsoleCommand;
import io.netty.channel.Channel;

import java.util.Scanner;

public class SendToUserConsoleCommand implements ConsoleCommand {

    public void exec(Scanner scanner, Channel channel) {
        System.out.println("请输入要发送的用户id: ");
        String toUserId = scanner.next();
        System.out.println("请输入要发送的内容: ");
        String message = scanner.next();
        channel.writeAndFlush(new MessageRequestPacket(toUserId, message));
    }
}
