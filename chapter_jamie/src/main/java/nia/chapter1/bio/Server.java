package nia.chapter1.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 无，Netty版代码
 * @author ZMJ
 * @date 2021/4/12
 */
public class Server {

    private ServerSocket serverSocket;

    public Server (int port) {
        try {
            this.serverSocket  = new ServerSocket(port);
            System.out.println("服务端启动成功，端口号：" + port);
        } catch (IOException e) {
            System.out.println("服务端启动失败");
            e.printStackTrace();
        }
    }

    public void start() {
        new Thread(this::doStart).start();
    }

    private void doStart() {
        while (true) {
            try {
                Socket client = serverSocket.accept(); // 阻塞等客户端
                new ClientHandler(client).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
