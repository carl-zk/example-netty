package com.example.netty.chapter10;

import java.net.InetSocketAddress;

import org.junit.jupiter.api.Test;

import com.example.netty.pojo.Order;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * 
 * @author Carl
 * @date 2024年6月11日 23:21:28
 */
public class HttpXmlServer {

	@Test
	void server() {
		try {
			new HttpXmlServer().run(8080);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void run(int port) throws Exception {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).option(ChannelOption.SO_BACKLOG, 1000)
					.handler(new LoggingHandler(LogLevel.DEBUG)).childHandler(new ChannelInitializer<SocketChannel>() {

						@Override
						protected void initChannel(SocketChannel ch) throws Exception {
							ch.pipeline().addLast("http-decoder", new HttpRequestDecoder())
									.addLast("http-aggregator", new HttpObjectAggregator(65535))
									.addLast("xml-decoder", new HttpXmlRequestDecoder(Order.class, true))
									.addLast("http-encoder", new HttpResponseEncoder())
									.addLast("xml-encoder", new HttpXmlResponseEncoder())
									.addLast("xmlServerHandler", new HttpXmlServerHandler());
						}
					});
			ChannelFuture f = b.bind(new InetSocketAddress(port)).sync();
			System.out.println("server started");
			f.channel().closeFuture().sync();
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}
}
