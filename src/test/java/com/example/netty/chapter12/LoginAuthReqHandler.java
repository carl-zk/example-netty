package com.example.netty.chapter12;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author Carl
 * @date 2024年6月15日 12:49:07
 */
@Slf4j
public class LoginAuthReqHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		log.debug("send a hand shake request");
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
				log.debug("login failed.");
				ctx.close();
			} else {
				log.debug("login success:{}", message);
				ctx.fireChannelRead(msg);
			}
		} else {
			ctx.fireChannelRead(msg);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.fireExceptionCaught(cause);
	}

}
