package com.example.netty.chapter12;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 *
 * @author Carl
 * @date 2024年6月15日 12:49:07
 */
public class LoginAuthReqHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// 发送握手请求
		// @formatter:off
		ctx.writeAndFlush(NettyMessage.builder()
				.header(Header.builder().crcCode(Header.CRC_CODE).type((byte) 3).build())
				.build());
		// @formatter:on
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		NettyMessage message = (NettyMessage) msg;
		if (message.getHeader() != null && 4 == message.getHeader().getType()) {
			byte loginResult = (byte) message.getBody();
			if (0 != loginResult) {
				System.out.println("login failed.");
				ctx.close();
			} else {
				System.out.println("login is ok:" + message);
				ctx.fireChannelRead(msg);
			}
		} else {
			ctx.fireChannelRead(msg);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.fireExceptionCaught(cause);
	}

}
