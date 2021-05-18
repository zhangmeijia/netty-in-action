package com.mj.service;

import com.mj.handler.UserServiceHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @author ZMJ
 * @date 2021/5/17
 */
public class UserServiceImpl implements UserService {

    public String sayHello(String word) {
        System.out.println("调用成功--参数：" + word);
        return "调用成功--参数：" + word;
    }

    // 服务启动类：ip地址 端口号
    public static void startServer(String hostName, int port) throws InterruptedException {
        //1.创建NioEventLoopGroup的两个实例对象
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        //2.创建服务启动辅助类：装配一些组件
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup)
                //指定服务器端监听套接字通道
                .channel(NioServerSocketChannel.class)
                //设置业务职责链
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new StringEncoder());
                        pipeline.addLast(new StringDecoder());
                        pipeline.addLast(new UserServiceHandler()); // 自定义的业务链
                    }
                });

        //3.绑定
        ChannelFuture f = bootstrap.bind(hostName, port).sync();
        // 阻塞等待客户端
        f.channel().closeFuture().sync();
    }
}
