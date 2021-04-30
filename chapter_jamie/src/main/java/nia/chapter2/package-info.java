/**
 * Jamie.
 *
 * Listing 1.1 编写Echo服务器和客户端
 * 应用程序很简单：客户端将消息发送给服务器，而服务器再将消息回送给客户端。
 *
 * Echo服务端
 * Listing 2.1 EchoServerHandler {@link nia.chapter2.echoserver.EchoServerHandler}
 * Listing 2.2 EchoServer class {@link nia.chapter2.echoserver.EchoServer}
 * EchoServerHandler实现了业务逻辑
 * main()方法引导了服务器：
 *  1，创建一个ServerBootstrap的实例以引导和绑定服务器；
 *  2，创建并分配一个NioEventLoopGroup实例以进行事件的处理，如接受新连接以及读/写数据；
 *  3，指定服务器绑定的本地的InetSocketAddress；
 *  4，使用一个EchoServerHandler的实例初始化每一个新的Channel；
 *  5，调用ServerBootstrap.bind()方法以绑定服务器。
 *
 *
 * Echo客户端将会：
 * （1）连接到服务器；
 * （2）发送一个或者多个消息；
 * （3）对于每个消息，等待并接收从服务器发回的相同的消息；
 * （4）关闭连接。
 * Listing 2.3 EchoClientHandler {@link nia.chapter2.echoclient.EchoClientHandler}
 * Listing 2.4 EchoClient class {@link nia.chapter2.echoclient.EchoClient}
 * main()方法引导了服务器：
 *  1，为初始化客户端，创建了一个Bootstrap实例；
 *  2，为进行事件处理分配了一个NioEventLoopGroup实例，其中事件处理包括创建新的连接以及处理入站和出站数据；
 *  3，为服务器连接创建了一个InetSocketAddress实例；
 *  4，当连接被建立时，一个EchoClientHandler实例会被安装到（该Channel的）ChannelPipeline中；
 *  5，在一切都设置完成后，调用Bootstrap.connect()方法连接到远程节点；
 *
 *
 *  启动测试：
 *  下面是发生的事：
 *  （1）一旦客户端建立连接，它就发送它的消息——Netty rocks!；
 *  （2）服务器报告接收到的消息，并将其回送给客户端；
 *  （3）客户端报告返回的消息并退出。
 *
 */
package nia.chapter2;