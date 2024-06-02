package com.example.aio.chapter4.server;

import java.util.Date;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 
 * @author Carl
 */
public class TimeServerHandler extends ChannelInboundHandlerAdapter {
	private int counter = 0;
	public static final String LINE_SEPARATOR = System.getProperty("line.separator");

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		String body = (String) msg;
		System.out.println("The TimeServer receive order : %s; the couter is %s".formatted(body, ++counter));
		String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ? new Date().toString() : "BAD ORDER";
		currentTime += LINE_SEPARATOR;
		ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes()); // 便捷工具，区别于 ByteBuffer,提供了readerIndex,writerIndex
		ChannelFuture future = ctx.writeAndFlush(resp);
		future.addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.close();
	}

}
