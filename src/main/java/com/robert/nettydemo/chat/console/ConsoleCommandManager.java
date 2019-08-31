package com.robert.nettydemo.chat.console;

import com.robert.nettydemo.chat.console.command.*;
import io.netty.channel.Channel;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ConsoleCommandManager implements ConsoleCommand {
    private final Map<String, ConsoleCommand> consoleCommandMap;

    private final String[] consoleCommand = new String[]{
            "sendToUser",
            "logout",
            "createGroup",
            "joinGroup",
            "quitGroup",
            "listGroupMembers",
            "sendToGroup"
    };

    public ConsoleCommandManager() {
        this.consoleCommandMap = new HashMap<>();
        consoleCommandMap.put(consoleCommand[0], new SendToUserConsoleCommand());
        consoleCommandMap.put(consoleCommand[1], new LogoutConsoleCommand());
        consoleCommandMap.put(consoleCommand[2], new CreateGroupConsoleCommand());
        consoleCommandMap.put(consoleCommand[3], new JoinGroupConsoleCommand());
        consoleCommandMap.put(consoleCommand[4], new QuitGroupConsoleCommand());
        consoleCommandMap.put(consoleCommand[5], new ListGroupMemberConsoleCommand());
        consoleCommandMap.put(consoleCommand[6], new SendToGroupConsoleCommand());

    }

    public void exec(Scanner scanner, Channel channel) {
        System.out.println("请输入指令：" + Arrays.asList(consoleCommand));
        String command = scanner.nextLine();
        ConsoleCommand consoleCommand = consoleCommandMap.get(command);

        if (consoleCommand != null) {
            consoleCommand.exec(scanner, channel);
        } else {
            System.out.println("无法识别 【" + command + "】指令！");
        }
    }
}
