package com.robert.nettydemo.chat.console;

import com.robert.nettydemo.chat.console.command.CreateGroupConsoleCommand;
import com.robert.nettydemo.chat.console.command.LogoutConsoleCommand;
import com.robert.nettydemo.chat.console.command.SendToUserConsoleCommand;
import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ConsoleCommandManager implements ConsoleCommand {
    private final Map<String, ConsoleCommand> consoleCommandMap;


    public ConsoleCommandManager() {
        this.consoleCommandMap = new HashMap<String, ConsoleCommand>();
        consoleCommandMap.put("sendToUser", new SendToUserConsoleCommand());
        consoleCommandMap.put("logout", new LogoutConsoleCommand());
        consoleCommandMap.put("createGroup", new CreateGroupConsoleCommand());

    }

    public void exec(Scanner scanner, Channel channel) {
        System.out.println("请输入指令：(sendToUser, logout, createGroup)");
        String command = scanner.nextLine();
        ConsoleCommand consoleCommand = consoleCommandMap.get(command);

        if (consoleCommand != null) {
            consoleCommand.exec(scanner, channel);
        } else {
            System.out.println("无法识别 【" + command + "】指令！");
        }
    }
}
