package com.example.aio.chapter5.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 
 * @author Carl
 */
@Sharable
public class EchoServerHandler extends ChannelInboundHandlerAdapter {
	int counter = 0;

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		String body = (String) msg;
		System.out.println("this is %s times receive client:%s".formatted(++counter, body));
		body += "$_";
		ByteBuf buf = Unpooled.copiedBuffer(body.getBytes());
		ctx.writeAndFlush(buf);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

}
