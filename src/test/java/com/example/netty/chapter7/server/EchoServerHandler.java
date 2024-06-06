package com.example.netty.chapter7.server;

import com.example.netty.chapter7.UserInfo;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 
 * @author Carl
 */
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		UserInfo userInfo = (UserInfo) msg;
		System.out.println("receive:%s".formatted(userInfo));
		userInfo.setAge(100);
		ctx.channel().writeAndFlush(userInfo).addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

}
