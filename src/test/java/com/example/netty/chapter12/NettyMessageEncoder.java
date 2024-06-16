package com.example.netty.chapter12;

import java.util.Map;

import org.springframework.util.Assert;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 *
 * @author Carl
 * @date 2024年6月13日 22:56:59
 */
public class NettyMessageEncoder extends MessageToByteEncoder<NettyMessage> {
	MarshallingEncoder encoder;

	/**
	 *
	 */
	public NettyMessageEncoder() {
		this.encoder = MarshallingEncoder.INSTANCE;
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

	@Override
	protected void encode(ChannelHandlerContext ctx, NettyMessage msg, ByteBuf out) throws Exception {
		Assert.isTrue(msg != null && msg.getHeader() != null, "The encode message is null");
		int writerPos = out.writerIndex();
		out.writeInt(msg.getHeader().getCrcCode());
		out.writeInt(msg.getHeader().getLength());
		out.writeLong(msg.getHeader().getSessionId());
		out.writeByte(msg.getHeader().getType());
		out.writeByte(msg.getHeader().getPriority());
		out.writeInt(msg.getHeader().getAttachment().size());
		for (Map.Entry<String, Object> param : msg.getHeader().getAttachment().entrySet()) {
			String key = param.getKey();
			byte[] keyArray = key.getBytes("UTF-8");
			out.writeInt(keyArray.length);
			out.writeBytes(keyArray);
			Object value = param.getValue();
			this.encoder.encode(ctx, value, out);
		}
		if (msg.getBody() != null) {
			this.encoder.encode(ctx, msg.getBody(), out);
		} else {
			out.writeInt(0);
		}
		out.setInt(writerPos + 4, out.writerIndex() - writerPos - 8); // value需要设置成total - lengthFieldEndOffset
	}

}
