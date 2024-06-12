package com.example.netty.chapter11;

import java.net.InetSocketAddress;

import org.junit.jupiter.api.Test;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 *
 * @author Carl
 * @date 2024年6月12日 19:51:04
 */
public class WebSocketServer {

	@Test
	void start() {
		try {
			new WebSocketServer().run(8090);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void run(int port) throws InterruptedException {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).option(ChannelOption.SO_BACKLOG, 1000)
					.handler(new LoggingHandler(LogLevel.DEBUG)).childHandler(new ChannelInitializer<SocketChannel>() {

						@Override
						protected void initChannel(SocketChannel ch) throws Exception {
							ch.pipeline()
									// combination of HttpRequestDecoder and HttpResponseEncoder
									.addLast("http-codec", new HttpServerCodec())
									.addLast("aggregator", new HttpObjectAggregator(65535))
									// send large data
									.addLast("http-chunked", new ChunkedWriteHandler())
									.addLast("hander", new WebSocketServerHandler());
						}
					});
			ChannelFuture f = b.bind(new InetSocketAddress(port)).sync();
			f.channel().closeFuture().sync();
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}
}
