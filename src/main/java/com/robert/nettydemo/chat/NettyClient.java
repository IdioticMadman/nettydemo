package com.robert.nettydemo.chat;

import com.robert.nettydemo.chat.bean.packet.MessageRequestPacket;
import com.robert.nettydemo.chat.codec.PacketCodeC;
import com.robert.nettydemo.chat.handler.*;
import com.robert.nettydemo.chat.util.LoginUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
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
                                .addLast(new Spliter())
                                .addLast(new PacketDecoder())
                                .addLast(new LoginResponseHandler())
                                .addLast(new MessageResponseHandler())
                                .addLast(new PacketEncoder());
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
                while (!Thread.interrupted()) {
                    //客户端已登录
                    if (LoginUtil.hasLogin(channel)) {
                        System.out.println("输入消息发送到服务端：");
                        Scanner sc = new Scanner(System.in);
                        String text = sc.nextLine();

                        MessageRequestPacket requestPacket = new MessageRequestPacket();
                        requestPacket.setMessage(text);
                        channel.writeAndFlush(requestPacket);
                    }
                }
            }
        }.start();
    }
}
