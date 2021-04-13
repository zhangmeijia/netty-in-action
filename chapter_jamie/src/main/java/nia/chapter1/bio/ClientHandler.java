package nia.chapter1.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * 服务端处理客户端的请求类
 * @author ZMJ
 * @date 2021/4/12
 */
public class ClientHandler {
    private final Socket socket;
    public ClientHandler(Socket client) {
        this.socket = client;
    }

    public void start() {
        System.out.println("新客户端接入");
        new Thread(this::doStart).start();
    }

    private void doStart() {
        try {
            InputStream inputStream = socket.getInputStream();
            while (true) {
               byte[] data = new byte[1024];
               int len;
               while ((len = inputStream.read(data)) != -1) {
                   String message = new String(data, 0, len);
                   System.out.println("客户端传来的消息：" + message);
                   socket.getOutputStream().write(data); // 写回去
               }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
