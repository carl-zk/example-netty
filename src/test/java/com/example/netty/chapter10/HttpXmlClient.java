package com.example.netty.chapter10;

import java.net.InetSocketAddress;

import org.junit.jupiter.api.Test;

import com.example.netty.pojo.Order;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * 
 * @author Carl
 * @date 2024年6月11日 22:46:34
 */
public class HttpXmlClient {

	@Test
	void client() {
		new HttpXmlClient().connect(8080);
	}

	public void connect(int port) {
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(group).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true)
					.handler(new LoggingHandler(LogLevel.DEBUG)).handler(new ChannelInitializer<SocketChannel>() {

						@Override
						protected void initChannel(SocketChannel ch) throws Exception {
							ch.pipeline().addLast("http-decoder", new HttpResponseDecoder())
									.addLast("http-aggregate", new HttpObjectAggregator(65535))
									.addLast("xml-decoder", new HttpXmlResponseDecoder(Order.class, true))
									.addLast("http-encoder", new HttpRequestEncoder())
									.addLast("xml-encoder", new HttpXmlRequestEncoder())
									.addLast("xmlClientHandler", new HttpXmlClientHandler());

						}
					});
			ChannelFuture f = b.connect(new InetSocketAddress(port)).sync();
			System.out.println("client started");
			f.channel().closeFuture().sync();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			group.shutdownGracefully();
		}
	}
}
