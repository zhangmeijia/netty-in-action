package com.mj.client;

import com.mj.service.UserService;

/**
 * @author ZMJ
 * @date 2021/5/17
 */
public class ClientBootStrap {

    public static final String providerName = "UserService#sayHello#";

    public static void main(String[] args) throws InterruptedException {
        RpcConsumer consumer = new RpcConsumer();
        UserService proxy = (UserService)consumer.createProxy(UserService.class, providerName);

        while (true) {
            Thread.sleep(2000);
            System.out.println(proxy.sayHello("are you ok?"));
        }
    }
}
