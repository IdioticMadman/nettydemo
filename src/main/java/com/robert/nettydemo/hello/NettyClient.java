package com.robert.nettydemo.hello;


import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.util.concurrent.TimeUnit;

public class NettyClient {

    public static final int MAX_RETRY = 5;

    public static void main(String[] args) throws InterruptedException {
        //客户端辅助类
        Bootstrap bootstrap = new Bootstrap();
        //处理数据读写
        NioEventLoopGroup group = new NioEventLoopGroup();
        bootstrap.group(group)
                //设置channel的属性
                .attr(AttributeKey.newInstance("clientName"), "nettyClient")
                //连接超时
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                //是否开启Nagle算法
                .option(ChannelOption.TCP_NODELAY, true)
                //启动底层心跳机制
                .option(ChannelOption.SO_KEEPALIVE, true)
                //指定io模型
                .channel(NioSocketChannel.class)
                //处理数据
                .handler(new ChannelInitializer<Channel>() {
                    protected void initChannel(Channel channel) throws Exception {
                        //添加逻辑处理器
                        channel.pipeline().addLast(new FirstClientHandler());
                    }
                });
        connect(bootstrap, "127.0.0.1", 8000, MAX_RETRY);
    }

    private static void connect(final Bootstrap bootstrap, final String host, final int port, final int retry) {
        bootstrap.connect(host, port)
                .addListener(new GenericFutureListener<Future<? super Void>>() {
                    public void operationComplete(Future<? super Void> future) throws Exception {
                        if (future.isSuccess()) {
                            System.out.println("客户端连接成功！");
                        } else if (retry == 0) {
                            System.out.println("重试机会用尽，放弃连接！");
                        } else {
                            final int time = MAX_RETRY - retry;
                            int delay = 1 << time;
                            System.out.println("客户端连接失败，开始第" + time + "重试。");
                            bootstrap.config()
                                    .group()
                                    .schedule(new Runnable() {
                                        public void run() {
                                            connect(bootstrap, host, port, time);
                                        }
                                    }, delay, TimeUnit.SECONDS);
                        }
                    }
                });
    }

}
