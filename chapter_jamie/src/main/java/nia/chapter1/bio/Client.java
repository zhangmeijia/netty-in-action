package nia.chapter1.bio;

import java.io.IOException;
import java.net.Socket;

/**
 * 无，Netty版代码
 * @author ZMJ
 * @date 2021/4/12
 */
public class Client {

    public static final int port = 8999;
    private static final String LOCAL_HOST = "127.0.0.1";


    public static void main(String[] args) throws IOException {
        final Socket socket = new Socket(LOCAL_HOST, port);

        new Thread(() -> {
            System.out.println("客户端启动成功！");
            while (true) {
                String message = "hello netty !";
                System.out.println("客户端发送数据：" + message);
                try {
                    socket.getOutputStream().write(message.getBytes());
                } catch (IOException e) {
                    System.out.println("客户端写数据失败");
                }
                sleep();
            }
        }).start();
    }

    private static void sleep() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
