package com.sanshengshui.netty.server;

import java.util.concurrent.ThreadFactory;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.AdaptiveRecvByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

/**
 * @author 穆书伟
 * @date 2018年9月18号
 * @description 服务端启动程序
 */
public final class Server2 {
    private static final InternalLogger LOG = InternalLoggerFactory.getInstance(Server2.class);

    public  static void main(String[] args) throws Exception {
//        	ThreadFactory threadFactory = new ThreadFactory() {
//		        		@Override
//						public Thread newThread(Runnable r) {
//					        Thread ret = new Thread("nio");
//					        ret.setDaemon(true);
//					        return ret;
//						}
//					};
			ThreadFactory threadFactory = new NamedThreadFactory("Server Acceptor NIO Thread", true);
        	//new ThreadFactoryBuilder().setNameFormat(getServerName() + " Server Acceptor NIO Thread#%d").build();

            EventLoopGroup bossGroup = new NioEventLoopGroup(5, threadFactory);
            EventLoopGroup workerGroup = new NioEventLoopGroup(5, threadFactory);
            try {

            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ChannelPipeline pipeline = ch.pipeline();
//                    pipeline.addLast("readTimeoutHandler", new ReadTimeoutHandler(DeviceServerListener.this.timeoutSeconds));
//                    pipeline.addLast("lineBasedFrameDecoder-" + maxLength, new LineBasedFrameDecoder(Integer.parseInt(maxLength)));// 按行('\n')解析成命令ByteBuf
//                    pipeline.addLast("stringPluginMessageDecoder", new StringDecoder(CharsetUtil.UTF_8));
//                    pipeline.addLast("stringToByteEncoder", new StringToByteEncoder());// 将JSON字符串类型消息转换成ByteBuf
//                    pipeline.addLast("deviceMessageDecoder", new DeviceMessageDecoder());// 将JSON字符串消息转成deviceMessage对象
//                    pipeline.addLast("deviceMessageEncoder", new DeviceMessageEncoder());// 将deviceMessage对象转成JSON字符串
//                    pipeline.addLast("deviceHeartBeatResponseHandler", new DeviceHeartBeatResponseHandler(heartTime));
//                    pipeline.addLast("deviceAuthResponseHandler",
//                            new DeviceAuthResponseHandler(DeviceServerListener.this.timeoutSeconds, DeviceServerListener.serverInstanceName));
//                    pipeline.addLast("deviceMessageHandler", new DeviceMessageHandler());

                    // log.debug("Added Handler to Pipeline: {}", pipeline.names());
                }
            }).option(ChannelOption.SO_BACKLOG, 1024).option(ChannelOption.SO_KEEPALIVE, true).option(ChannelOption.TCP_NODELAY, true)
                    .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT).childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                    .option(ChannelOption.RCVBUF_ALLOCATOR, AdaptiveRecvByteBufAllocator.DEFAULT);

            // Start the server. Bind and start to accept incoming connections.
            ChannelFuture channelFuture = bootstrap.bind(8889).sync();
        } catch (Throwable e) {
        	LOG.error("---error", e);
		} finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
