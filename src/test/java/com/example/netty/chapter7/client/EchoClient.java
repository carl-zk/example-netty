package com.example.netty.chapter7.client;

import org.junit.jupiter.api.Test;

import com.example.netty.chapter7.msgpack.MsgpackDecoder;
import com.example.netty.chapter7.msgpack.MsgpackEncoder;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

/**
 * VM 参数 --add-opens java.base/java.lang=ALL-UNNAMED
 * 
 * @author Carl
 */
public class EchoClient {

	@Test
	void start() {
		new EchoClient().connect("localhost", 8080);
	}

	public void connect(String host, int port) {
		NioEventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(group).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true)
					.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000)
					.handler(new ChannelInitializer<SocketChannel>() {

						@Override
						protected void initChannel(SocketChannel ch) throws Exception {
							ch.pipeline().addLast("frameEncoder", new LengthFieldPrepender(2))
									.addLast("msgpack encoder", new MsgpackEncoder())
									.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(65535, 0, 2, 0, 2))
									.addLast("msgpack decoder", new MsgpackDecoder()).addLast(new EchoClientHandler());
						}
					});
			ChannelFuture f = b.connect(host, port).sync();
			f.channel().closeFuture().sync();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			group.shutdownGracefully();
		}
	}
}
