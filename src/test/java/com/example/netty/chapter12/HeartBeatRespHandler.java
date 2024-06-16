package com.example.netty.chapter12;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author Carl
 * @date 2024年6月15日 18:38:19
 */
@Slf4j
public class HeartBeatRespHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		NettyMessage message = (NettyMessage) msg;
		if (message.getHeader() != null && message.getHeader().getType() == 5) {
			log.debug("receive Client heart beat request:" + message);
			NettyMessage heartBeat = NettyMessage.builder()
					.header(Header.builder().crcCode(Header.CRC_CODE).type((byte) 6).build()).build();
			ctx.writeAndFlush(heartBeat);
		} else {
			ctx.fireChannelRead(msg);
		}
	}
}
