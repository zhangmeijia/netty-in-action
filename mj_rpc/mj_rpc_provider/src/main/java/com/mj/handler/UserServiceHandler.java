package com.mj.handler;

import com.mj.service.UserService;
import com.mj.service.UserServiceImpl;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author ZMJ
 * @date 2021/5/17
 */
public class UserServiceHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 判断是否符合约定，符合则调用本地方法，返回数据
        // msg：UserService#sayHello#are you ok?  (自己约定的协议)
        if (msg.toString().startsWith("UserService")) {
            UserService userService = new UserServiceImpl();
            String result = userService.sayHello(msg.toString().substring(msg.toString().lastIndexOf("#") + 1));
            ctx.writeAndFlush(result);
        }
    }

}
