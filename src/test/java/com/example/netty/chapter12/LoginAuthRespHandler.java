package com.example.netty.chapter12;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 *
 * @author Carl
 * @date 2024年6月15日 13:29:00
 */
public class LoginAuthRespHandler extends ChannelInboundHandlerAdapter {
	private Map<String, Boolean> nodeCheck = new ConcurrentHashMap<>();
	private String[] whiteList = { "127.0.0.1" };

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		NettyMessage message = (NettyMessage) msg;
		if (message.getHeader() != null && 3 == message.getHeader().getType()) {
			String nodeIndex = ctx.channel().remoteAddress().toString();
			System.out.println("nodeIndex:" + nodeIndex);
			NettyMessage loginResp = null;
			if (this.nodeCheck.containsKey(nodeIndex)) {
				// 重复登录，拒绝
				loginResp = this.build((byte) -1);
			} else {
				InetSocketAddress remoteIp = (InetSocketAddress) ctx.channel().remoteAddress();
				String ip = remoteIp.getAddress().getHostAddress();
				boolean isAllowed = false;
				for (String wip : this.whiteList) {
					if (wip.equals(ip)) {
						isAllowed = true;
						break;
					}
				}
				loginResp = isAllowed ? this.build((byte) 0) : this.build((byte) -1);
				if (isAllowed) {
					this.nodeCheck.put(nodeIndex, true);
				}
			}
			System.out.println("The login response is:" + loginResp);
			ctx.writeAndFlush(loginResp);
		} else {
			ctx.fireChannelRead(msg);
		}
	}

	private NettyMessage build(byte result) {
		return NettyMessage.builder().header(Header.builder().crcCode(Header.CRC_CODE).type((byte) 4).build())
				.body(result).build();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		this.nodeCheck.remove(ctx.channel().remoteAddress().toString());
		ctx.close();
		ctx.fireExceptionCaught(cause);
	}

}
