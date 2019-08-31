package com.robert.nettydemo.chat.client;

import com.robert.nettydemo.chat.client.handler.*;
import com.robert.nettydemo.chat.protocol.codec.PacketDecoder;
import com.robert.nettydemo.chat.protocol.codec.PacketEncoder;
import com.robert.nettydemo.chat.protocol.codec.Spliter;
import com.robert.nettydemo.chat.console.ConsoleCommand;
import com.robert.nettydemo.chat.console.ConsoleCommandManager;
import com.robert.nettydemo.chat.console.command.LoginConsoleCommand;
import com.robert.nettydemo.chat.handler.IMIdleStateHandler;
import com.robert.nettydemo.chat.util.SessionUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.util.Date;
import java.util.Scanner;

public class NettyClient {

    public static void main(String[] args) {
        Bootstrap bootstrap = new Bootstrap();
        EventLoopGroup group = new NioEventLoopGroup();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline()
                                .addLast(new IMIdleStateHandler())
                                .addLast(new Spliter())
                                .addLast(new PacketDecoder())
                                .addLast(new LoginResponseHandler())
                                .addLast(new LogoutResponseHandler())
                                .addLast(new CreateGroupResponseHandler())
                                .addLast(new JoinGroupResponseHandler())
                                .addLast(new QuitGroupResponseHandler())
                                .addLast(new ListGroupMembersResponseHandler())
                                .addLast(new MessageResponseHandler())
                                .addLast(new GroupMessageResponseHandler())
                                .addLast(new PacketEncoder())
                                .addLast(new HeartBeatTimerHandler());
                    }
                })
                .connect("127.0.0.1", 8000)
                .addListener(new GenericFutureListener<Future<? super Void>>() {
                    public void operationComplete(Future<? super Void> future) throws Exception {
                        if (future.isSuccess()) {
                            System.out.println(new Date() + ": 客户端连接成功");
                            Channel channel = ((ChannelFuture) future).channel();
                            startConsoleThread(channel);
                        } else {
                            System.out.println(new Date() + ": 连接失败");
                        }
                    }
                });
    }

    private static void startConsoleThread(final Channel channel) {
        new Thread() {
            @Override
            public void run() {
                ConsoleCommand loginConsoleCommand = new LoginConsoleCommand();
                ConsoleCommand consoleCommandManager = new ConsoleCommandManager();
                while (!Thread.interrupted()) {
                    //客户端已登录
                    Scanner sc = new Scanner(System.in);
                    if (!SessionUtil.hasLogin(channel)) {
                        loginConsoleCommand.exec(sc, channel);
                        waitForLoginResponse();
                    } else {
                        consoleCommandManager.exec(sc, channel);
                    }
                }
            }
        }.start();
    }

    private static void waitForLoginResponse() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
