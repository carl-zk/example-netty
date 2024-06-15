package com.example.netty.chapter12;

import java.util.List;
import java.util.Map;

import org.springframework.util.Assert;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

/**
 *
 * @author Carl
 * @date 2024年6月13日 22:56:59
 */
@Sharable
public class NettyMessageEncoder extends MessageToMessageEncoder<NettyMessage> {
	MarshallingEncoder encoder;

	/**
	 *
	 */
	public NettyMessageEncoder() {
		this.encoder = new MarshallingEncoder();
	}

	@Override
	protected void encode(ChannelHandlerContext ctx, NettyMessage msg, List<Object> out) throws Exception {
		Assert.isTrue(msg != null && msg.getHeader() != null, "The encode message is null");
		ByteBuf sendBuf = Unpooled.buffer();
		sendBuf.writeInt(msg.getHeader().getCrcCode());
		sendBuf.writeInt(msg.getHeader().getLength());
		sendBuf.writeLong(msg.getHeader().getSessionId());
		sendBuf.writeByte(msg.getHeader().getType());
		sendBuf.writeByte(msg.getHeader().getPriority());
		sendBuf.writeInt(msg.getHeader().getAttachment().size());
		for (Map.Entry<String, Object> param : msg.getHeader().getAttachment().entrySet()) {
			String key = param.getKey();
			byte[] keyArray = key.getBytes("UTF-8");
			sendBuf.writeInt(keyArray.length);
			sendBuf.writeBytes(keyArray);
			Object value = param.getValue();
			this.encoder.encode(ctx, value, sendBuf);
		}
		if (msg.getBody() != null) {
			this.encoder.encode(ctx, msg.getBody(), sendBuf);
		} else {
			sendBuf.writeInt(0);
		}
		sendBuf.setInt(4, sendBuf.readableBytes());
		out.add(sendBuf);
	}

}
