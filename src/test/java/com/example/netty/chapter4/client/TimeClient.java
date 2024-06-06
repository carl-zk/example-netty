package com.example.netty.chapter4.client;

import org.junit.jupiter.api.Test;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * 
 * @author Carl
 */
public class TimeClient {

	@Test
	void start() {
		int port = 8080;
		try {
			new TimeClient().connect(port, "127.0.0.1");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void connect(int port, String host) throws Exception {
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(group).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true)
					.option(ChannelOption.SO_KEEPALIVE, true).handler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel ch) throws Exception {
							ch.pipeline().addLast(new LineBasedFrameDecoder(1024)).addLast(new StringDecoder())
									.addLast(new TimeClientHandler());
						}
					});
			ChannelFuture f = b.connect(host, port).sync();

			f.channel().closeFuture().sync();
		} finally {
			group.shutdownGracefully();
		}
	}

}
