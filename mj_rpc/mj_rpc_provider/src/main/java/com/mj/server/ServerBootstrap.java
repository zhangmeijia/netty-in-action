package com.mj.server;

import com.mj.service.UserServiceImpl;

/**
 * @author ZMJ
 * @date 2021/5/17
 */
public class ServerBootstrap {

    // 服务端的启动类
    public static void main(String[] args) throws InterruptedException {
        UserServiceImpl.startServer("127.0.0.1", 8000);
    }

}
