package nia.chapter1.bio;

/**
 * 无，Netty版代码
 * @author ZMJ
 * @date 2021/4/12
 */
public class ServerBoot {

    public static final int port = 8999;

    public static void main(String[] args) {
        Server server = new Server(port);
        server.start();
    }
}
