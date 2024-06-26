package com.example.netty.chapter8;

import com.example.netty.chapter8.pojo.SubscribeReq;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 
 * @author Carl
 */
public class SubReqClientHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		for (int i = 0; i < 10; i++) {
			ctx.write(subReq(i));

		}
		ctx.flush();
	}

	SubscribeReq subReq(int i) {
		return SubscribeReq.newBuilder().setSubReqId(i).setUserName("Lily").setProductName("Netty").addAddress("add1")
				.addAddress("add2").build();
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		System.out.println("receive from server:%s".formatted(msg));
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
