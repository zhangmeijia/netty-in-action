package nia.chapter2.echoserver;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author ZMJ
 * @date 2021/4/13
 */
public class EchoServer {
    private final int port;
    public EchoServer (int port) {
        this.port = port;
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.err.println("Usage：" + EchoServer.class.getSimpleName());
            return;
        }

        int port = Integer.parseInt(args[0]); //←设置端口值（如果端口参数的格式不正确，则抛出一个NumberFormatException）
        new EchoServer(port).start(); //←调用服务器的start()方法
    }

    private void start() throws Exception {
        final EchoServerHandler serverHandler = new EchoServerHandler();
        EventLoopGroup group = new NioEventLoopGroup(); //←❶创建EventLoopGroup
        try {
            ServerBootstrap b = new ServerBootstrap(); //←❷创建ServerBootstrap
            b.group(group)
                    .channel(NioServerSocketChannel.class)//←❸指定所使用的NIO传输Channel
                    .localAddress(port) //←❹使用指定的端口设置套接字地址
                    .childHandler(new ChannelInitializer() { //←❺添加一个EchoServerHandler到子Channel的ChannelPipeline
                        @Override
                        protected void initChannel(Channel ch) throws Exception {
                            ch.pipeline().addLast(serverHandler);
                        }
                    });
            ChannelFuture f = b.bind().sync(); //←❻异步地绑定服务器；调用sync()方法阻塞等待直到绑定完成
            f.channel().closeFuture().sync(); //←❼获取Channel的CloseFuture，并且阻塞当前线程直到它完成
        } catch (Exception e) {
        } finally {
            group.shutdownGracefully().sync(); //←❽关闭EventLoopGroup，释放所有的资源
        }

    }


}
