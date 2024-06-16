package com.example.netty.chapter12;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 *
 * @author Carl
 * @date 2024年6月15日 22:09:51
 */
public class NettyClient {

	@Test
	void client() throws InterruptedException {
		new NettyClient().connect("localhost", 8080);
		Thread.currentThread().join();
	}

	private ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
	EventLoopGroup group = new NioEventLoopGroup();

	public void connect(String host, int port) {
		try {
			Bootstrap b = new Bootstrap();
			b.group(this.group).channel(NioSocketChannel.class).option(ChannelOption.SO_KEEPALIVE, true)
					.option(ChannelOption.TCP_NODELAY, true).handler(new LoggingHandler(LogLevel.DEBUG))
					.handler(new ChannelInitializer<SocketChannel>() {

						@Override
						protected void initChannel(SocketChannel ch) throws Exception {
							ch.pipeline().addLast(new NettyMessageDecoder(1024 * 1024, 4, 4))
									.addLast("MessageEncoder", new NettyMessageEncoder())
									// .addLast(new ReadTimeoutHandler(50))
									.addLast(new LoginAuthReqHandler()).addLast(new HeartBeatReqHandler());

						}
					});
			ChannelFuture f = b.connect(new InetSocketAddress(host, port),
					// @formmater:off
					new InetSocketAddress("localhost", 9080)) // 绑定client端端口
					// @formmater:on
					.sync();
			f.channel().closeFuture().sync();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 重连
			this.executor.execute(() -> {
				try {
					TimeUnit.SECONDS.sleep(5);
					try {
						this.connect(host, port);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		}
	}
}
