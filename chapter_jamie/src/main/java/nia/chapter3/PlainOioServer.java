package nia.chapter3;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;

/**
 * 未使用Netty的同步编程
 * @author ZMJ
 * @date 2021/4/16
 */
public class PlainOioServer {

    public void server() throws Exception {
        final ServerSocket serverSocket = new ServerSocket(8999);
        while (true) {
            final Socket clientSocket = serverSocket.accept(); // 阻塞等待客户端连接
            System.out.println("Accept client connection.");
            new Thread(new Runnable() { // 创建一个新的线程来处理客户端请求
                @Override
                public void run() {
                    OutputStream out = null;
                    try {
                        out = clientSocket.getOutputStream();
                        out.write("我收到你的请求了".getBytes(Charset.forName("UTF-8"))); // 将这条消息写给客户端
                        out.flush();
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        if (null != out) {
                            try {
                                out.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }).start(); // 启动服务端线程
        }
    }


}
