package com.robert.nettydemo.chat.console.command;

import com.robert.nettydemo.chat.protocol.packet.LoginRequestPacket;
import com.robert.nettydemo.chat.console.ConsoleCommand;
import io.netty.channel.Channel;

import java.util.Date;
import java.util.Scanner;
import java.util.UUID;

public class LoginConsoleCommand implements ConsoleCommand {

    public void exec(Scanner scanner, Channel channel) {
        System.out.println("输入用户名进行登录：");
        String name = scanner.nextLine();
        System.out.println(new Date() + ": 客户端开始登录");
        LoginRequestPacket packet = new LoginRequestPacket();
        packet.setUuid(UUID.randomUUID().toString().split("-")[0]);
        packet.setUsername(name);
        packet.setPassword("123456");
        channel.writeAndFlush(packet);
    }
}
