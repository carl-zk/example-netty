package com.example.netty.chapter12;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 *
 * @author Carl
 * @date 2024年6月15日 18:38:19
 */
public class HeartBeatRespHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		NettyMessage message = (NettyMessage) msg;
		if (message.getHeader() != null && message.getHeader().getType() == 5) {
			System.out.println("receive Client heart beat message:" + message);
			NettyMessage heartBeat = NettyMessage.builder()
					.header(Header.builder().crcCode(Header.CRC_CODE).type((byte) 6).build()).build();
			System.out.println("send heart beat response:" + heartBeat);
			ctx.writeAndFlush(heartBeat);
		} else {
			ctx.fireChannelRead(msg);
		}
		super.channelRead(ctx, msg);
	}
}
