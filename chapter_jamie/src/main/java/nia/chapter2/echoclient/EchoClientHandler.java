package nia.chapter2.echoclient;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * @author ZMJ
 * @date 2021/4/13
 *
 * SimpleChannelInboundHandler类以处理所有必须的任务
 * channelActive()——在到服务器的连接已经建立之后将被调用；
 * channelRead0()——当从服务器接收到一条消息时被调用；
 * exceptionCaught()——在处理过程中引发异常时被调用。
 */
@ChannelHandler.Sharable //←标记该类的实例可以被多个Channel共享
public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ctx.writeAndFlush(Unpooled.copiedBuffer("Netty rocks!", CharsetUtil.UTF_8)); //←当被通知Channel是活跃的时候，发送一条消息
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf in) {
        System.out.println("Client received：" + in.toString(CharsetUtil.UTF_8)); //←记录已接收消息的转储
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close(); // ←在发生异常时，记录错误并关闭Channel
    }

}
