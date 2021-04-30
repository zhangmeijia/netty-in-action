package nia.chapter3;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/**
 * 通过Netty实现oio
 * @author ZMJ
 * @date 2021/4/19
 */
public class NettyOioServer {

    public void server(int port) {
        final ByteBuf byteBuf = Unpooled.unreleasableBuffer(Unpooled.copiedBuffer("Hi \r\n", Charset.forName("UTF-8")));


    }
}
