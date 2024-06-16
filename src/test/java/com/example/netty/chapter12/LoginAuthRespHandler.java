package com.example.netty.chapter12;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author Carl
 * @date 2024年6月15日 13:29:00
 */
@Slf4j
public class LoginAuthRespHandler extends ChannelInboundHandlerAdapter {
	private Map<String, Boolean> nodeCheck = new ConcurrentHashMap<>();
	private String[] whiteList = { "127.0.0.1" };

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		NettyMessage message = (NettyMessage) msg;
		String nodeIndex = ctx.channel().remoteAddress().toString();
		log.debug("nodeIndex is:{}", nodeIndex);
		if (message.getHeader() != null && 3 == message.getHeader().getType()) {
			log.debug("the hand shake request:" + message);
			NettyMessage loginResp = null;
			if (this.nodeCheck.containsKey(nodeIndex)) {
				// 重复登录，拒绝
				loginResp = this.build((byte) -1);
				log.info("{} 禁止重复登录", nodeIndex);
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
					log.info("{} login success", nodeIndex);
				}
			}
			ctx.writeAndFlush(loginResp);
		} else {
			if (this.nodeCheck.containsKey(nodeIndex)) {
				ctx.fireChannelRead(msg);
			} else {
				ctx.writeAndFlush(this.build((byte) -2));
			}
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
