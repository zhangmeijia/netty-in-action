package com.mj.client;

import com.mj.handler.UserClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author ZMJ
 * @date 2021/5/17
 */
public class RpcConsumer {

    // 创建一个线程池对象
    private static ExecutorService executor = Executors.newFixedThreadPool(5);
    private static UserClientHandler userClientHandler;

    //1.创建一个代理对象, providerName:代理对象在调用的时候具体传入的值，UserService#sayHello are you ok?
    public Object createProxy(final Class<?> serviceClass, final String providerName) {
        // 借助JDK动态代理生成代理对象
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new Class<?>[]{serviceClass}, new InvocationHandler() {
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        //（1）调用初始化netty客户端
                        if (userClientHandler == null)
                            initClient();
                        //（2）设置参数
                        userClientHandler.setPara(providerName + args[0]);
                        //（3）去服务端请求数据
                        return executor.submit(userClientHandler).get();
                    }
                });
    }

    //2.初始化Netty客户端
    public static void initClient() throws InterruptedException {
        userClientHandler = new UserClientHandler();

        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new StringEncoder());
                        pipeline.addLast(new StringDecoder());
                        pipeline.addLast(userClientHandler); // 自定义业务处理
                    }
                });
        ChannelFuture f = bootstrap.connect("127.0.0.1", 8000).sync();
    }
}
