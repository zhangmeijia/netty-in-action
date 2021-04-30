package nia.chapter3;

import io.netty.buffer.ByteBuf;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * 未使用Netty的异步编程
 * @author ZMJ
 * @date 2021/4/16
 */
public class PlainNioServer {

    private static final int port = 8999;

    public void server() throws Exception {

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);

        ServerSocket serverSocket = serverSocketChannel.socket();
        InetSocketAddress address = new InetSocketAddress(port);
        serverSocket.bind(address); // 将服务器绑定到指定的端口上

        Selector selector = Selector.open(); // 打开Selector处理channel
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT); // 将ServerChannel注册到selector上以接收连接
        final ByteBuffer msg = ByteBuffer.wrap("Hi! \r\n".getBytes());
        while (true) {
            try {
                selector.select(); // 等待需要处理的新事件；阻塞将一直持续到下一个传入事件
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
            Set<SelectionKey> selectionKeySet = selector.selectedKeys(); // 获取所有接收事件的Selection-Key实例
            Iterator<SelectionKey> iterator = selectionKeySet.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();
                try {
                    if (key.isAcceptable()) { // 检查事件是否是一个新的已经就绪可以被接受的连接
                        ServerSocketChannel server = (ServerSocketChannel) key.channel();
                        SocketChannel client = server.accept();
                        client.configureBlocking(false); // 设置为非阻塞
                        client.register(selector,
                                SelectionKey.OP_WRITE | SelectionKey.OP_WRITE,
                                msg.duplicate()); // 接受客户端，并将它注册到选择器中
                        System.out.println("Accepted connection from :" + client);
                    }
                    if (key.isWritable()) { // 检查套接字是否已经准备好写数据
                        SocketChannel client = (SocketChannel) key.channel();
                        ByteBuffer buffer = (ByteBuffer) key.attachment(); // attachment=附件
                        while (buffer.hasRemaining()) {
                            if (client.write(buffer) == 0) { // 将数据写到已经连接的客户端
                                break;
                            }
                        }
                        client.close(); // 关闭连接
                    }
                } catch (Exception e) {
                    key.cancel();
                    try {
                         key.channel().close();
                    } catch (Exception ex) {
                        // ignore on close
                    }
                }

            }
        }

    }
}
