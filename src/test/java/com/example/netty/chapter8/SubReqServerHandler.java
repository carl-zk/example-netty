package com.example.netty.chapter8;

import com.example.netty.chapter8.pojo.SubscribeReq;
import com.example.netty.chapter8.pojo.SubscribeResp;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 
 * @author Carl
 */
@Sharable
public class SubReqServerHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		SubscribeReq req = (SubscribeReq) msg;
		if ("Lily".equalsIgnoreCase(req.getUserName())) {
			System.out.println("receive from client:%s".formatted(req));
			ctx.writeAndFlush(resp(req.getSubReqId()));
		}
	}

	private SubscribeResp resp(int subReqId) {
		return SubscribeResp.newBuilder().setSubReqId(subReqId).setRespCode(subReqId).setDesc("Hi, OK").build();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

}
