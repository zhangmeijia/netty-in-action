package com.mj.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.Callable;

/**
 * @author ZMJ
 * @date 2021/5/17
 */
// ChannelInboundHandlerAdapter
// Callable 需要返回结果的
public class UserClientHandler extends ChannelInboundHandlerAdapter implements Callable {

    private ChannelHandlerContext context; // 缓存
    private String result;
    private String para; // 请求参数

    // 当channel连接成功的时候就会触发这个返回
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        this.context = ctx;
    }

    // 收到服务端数据，唤醒等待线程
    public synchronized void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        this.result = msg.toString();
        notify(); // 吵醒
    }

    // 写出数据，开始等待唤醒
    public synchronized Object call() throws Exception {
        context.writeAndFlush(para);
        wait(); // 等待
        return result;
    }

    // 设置参数
    public void setPara(String para) {
        this.para = para;
    }

}
