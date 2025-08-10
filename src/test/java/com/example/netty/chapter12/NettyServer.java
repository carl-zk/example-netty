package com.example.netty.chapter12;

import java.net.InetSocketAddress;

import org.junit.jupiter.api.Test;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;

/**
 *
 * @author Carl
 * @date 2024年6月15日 23:01:03
 */
public class NettyServer {
	@Test
	void server() {
		try {
			new NettyServer().bind(8080);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void bind(int port) throws InterruptedException {

		EventLoopGroup bossGroup = new NioEventLoopGroup(
				new ThreadFactoryBuilder().setNameFormat("boss-group-%d").build());
		EventLoopGroup workerGroup = new NioEventLoopGroup(
				new ThreadFactoryBuilder().setNameFormat("worker-group-%d").build());
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
					.option(ChannelOption.SO_BACKLOG, 1000)
					.childOption(ChannelOption.SO_KEEPALIVE, true)
					.childOption(ChannelOption.TCP_NODELAY, true)
					.handler(new LoggingHandler(LogLevel.DEBUG))
					.childHandler(new ChannelInitializer<SocketChannel>() {

						@Override
						protected void initChannel(SocketChannel ch) throws Exception {
							ch.pipeline().addLast(new NettyMessageDecoder(1024 * 1024, 4, 4))
									.addLast(new NettyMessageEncoder()).addLast(new ReadTimeoutHandler(50))
									.addLast(new LoginAuthRespHandler()).addLast(new HeartBeatRespHandler());
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