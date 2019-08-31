package com.robert.nettydemo.chat.server;

import com.robert.nettydemo.chat.client.handler.HeartBeatTimerHandler;
import com.robert.nettydemo.chat.handler.IMIdleStateHandler;
import com.robert.nettydemo.chat.protocol.codec.PacketCodecHandler;
import com.robert.nettydemo.chat.protocol.codec.Spliter;
import com.robert.nettydemo.chat.server.handler.*;
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
                                .addLast(new IMIdleStateHandler())
                                .addLast(new Spliter())//Spliter不是无状态的，需要每个链接都维护一个Spliter实例
                                .addLast(PacketCodecHandler.INSTANCE)
                                .addLast(LoginRequestHandler.INSTANCE)
                                .addLast(HeartBeatRequestHandler.INSTANCE)
                                .addLast(AuthHandler.INSTANCE)
                                .addLast(IMHandler.INSTANCE);
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
