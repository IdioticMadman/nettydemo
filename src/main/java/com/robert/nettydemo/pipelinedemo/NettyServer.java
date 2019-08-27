package com.robert.nettydemo.pipelinedemo;

import com.robert.nettydemo.hello.FirstServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

public class NettyServer {

    public static void main(String[] args) {
        //绑定线程模型，IO 模型，连接读写处理逻辑之外。还可以设置一些连接的属性
        ServerBootstrap bootstrap = new ServerBootstrap();
        //负责接受连接线程，创建新的连接
        NioEventLoopGroup boss = new NioEventLoopGroup();
        //负责读取数据的线程，读取数据以及业务逻辑
        NioEventLoopGroup worker = new NioEventLoopGroup();
        bootstrap.group(boss, worker)
                .channel(NioServerSocketChannel.class)
                //设置服务端的channel，临时存放已完成三次握手的请求的队列的最大长度
                .option(ChannelOption.SO_BACKLOG, 1024)
                //开启TCP底层心跳机制
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                //是否开启Nagle算法，true：关闭，false：开启。如果要求时延低，有数据立马发送，则关闭，如果需要减少网络交互，就打开
                .childOption(ChannelOption.TCP_NODELAY, true)
                //给服务端的channel指定自定义属性
                .attr(AttributeKey.newInstance("serverName"), "nettyServer")
                //每条channel连接设置属性
                .childAttr(AttributeKey.newInstance("clientKey"), "clientValue")
                //服务端启动过程中的一些逻辑
                .handler(new ChannelInitializer<NioServerSocketChannel>() {
                    protected void initChannel(NioServerSocketChannel nioSocketChannel) throws Exception {
                        System.out.println("服务端启动中。。。");
                    }
                })
                //指定处理新连接数据的读写处理逻辑
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        //添加逻辑处理器
                        ch.pipeline().addLast(new ChannelInBoundA());
                        ch.pipeline().addLast(new ChannelInBoundB());
                        ch.pipeline().addLast(new ChannelInBoundC());

                        ch.pipeline().addLast(new ChannelOutBoundA());
                        ch.pipeline().addLast(new ChannelOutBoundB());
                        ch.pipeline().addLast(new ChannelOutBoundC());
                    }
                });
        bind(bootstrap, 8000);
    }

    private static void bind(final ServerBootstrap bootstrap, final int port) {
        bootstrap.bind(port).addListener(new GenericFutureListener<Future<? super Void>>() {
            public void operationComplete(Future<? super Void> future) throws Exception {
                if (future.isSuccess()) {
                    System.out.println("绑定端口成功：" + port);
                } else {
                    System.out.println("绑定端口失败：" + port);
                    bind(bootstrap, port + 1);
                }
            }
        });
    }
}
