package com.robert.nettydemo.chat.server;

import com.robert.nettydemo.chat.protocol.codec.PacketDecoder;
import com.robert.nettydemo.chat.protocol.codec.PacketEncoder;
import com.robert.nettydemo.chat.protocol.codec.Spliter;
import com.robert.nettydemo.chat.server.handler.AuthHandler;
import com.robert.nettydemo.chat.server.handler.LoginRequestHandler;
import com.robert.nettydemo.chat.server.handler.MessageRequestHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.util.Date;

public class NettyServer {
    public static void main(String[] args) {
        ServerBootstrap bootstrap = new ServerBootstrap();
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        bootstrap.group(boss, worker)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline()
                                // .addLast(new LifeCycleTestHandler())
                                .addLast(new Spliter())
                                .addLast(new PacketDecoder())
                                .addLast(new LoginRequestHandler())
                                .addLast(new AuthHandler())
                                .addLast(new MessageRequestHandler())
                                .addLast(new PacketEncoder());
                    }
                })
                .handler(new ChannelInitializer<NioServerSocketChannel>() {
                    protected void initChannel(NioServerSocketChannel ch) throws Exception {
                        System.out.println(new Date() + ": 服务端启动中");
                    }
                })
                .bind(8000)
                .addListener(new GenericFutureListener<Future<? super Void>>() {
                    public void operationComplete(Future<? super Void> future) throws Exception {
                        if (future.isSuccess()) {
                            System.out.println(new Date() + ": 服务端启动成功");
                        } else {
                            System.out.println(new Date() + ": 服务端启动失败");
                        }

                    }
                });

    }
}
