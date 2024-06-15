package com.example.netty.chapter12;

import java.util.concurrent.TimeUnit;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.concurrent.ScheduledFuture;

/**
 *
 * @author Carl
 * @date 2024年6月15日 18:09:04
 */
public class HeartBeatReqHandler extends ChannelInboundHandlerAdapter {
	private volatile ScheduledFuture<?> heartBeat;

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		NettyMessage message = (NettyMessage) msg;
		if (message.getHeader() != null && message.getHeader().getType() == 4) {
			// 握手响应
			this.heartBeat = ctx.executor().scheduleAtFixedRate(new HeartBeatTask(ctx), 0, 5000, TimeUnit.MILLISECONDS);
		} else if (message.getHeader() != null && message.getHeader().getType() == 6) {
			// 心跳响应
			System.out.println("Client receive Server heart beat message: --->" + message);
		} else {
			ctx.fireChannelRead(msg);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		if (this.heartBeat != null) {
			this.heartBeat.cancel(true);
			this.heartBeat = null;
		}
		ctx.fireExceptionCaught(cause);
	}

	private class HeartBeatTask implements Runnable {
		private final ChannelHandlerContext ctx;

		/**
		 * @param ctx
		 */
		public HeartBeatTask(ChannelHandlerContext ctx) {
			this.ctx = ctx;
		}

		@Override
		public void run() {
			NettyMessage heatBeat = NettyMessage.builder()
					.header(Header.builder().crcCode(Header.CRC_CODE).type((byte) 5).build()).build();
			System.out.println(heatBeat);
			this.ctx.writeAndFlush(heatBeat);
		}

	}
}
