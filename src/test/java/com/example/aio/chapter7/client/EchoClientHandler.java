package com.example.aio.chapter7.client;

import java.util.UUID;

import com.example.aio.chapter7.UserInfo;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 
 * @author Carl
 */
public class EchoClientHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		for (int i = 0; i < 2; i++) {
			ctx.writeAndFlush(UserInfo.builder().name(UUID.randomUUID().toString()).age(i).build())
					.addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE); // 这个listener很管用
		}
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		System.out.println("receive:%s".formatted(msg));
		// ctx.write(msg); // 又给写回了
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

}
