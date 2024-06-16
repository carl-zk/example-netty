package com.example.netty.chapter12;

import java.util.HashMap;
import java.util.Map;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 *
 * @author Carl
 * @date 2024年6月14日 22:17:42
 */
public class NettyMessageDecoder extends LengthFieldBasedFrameDecoder {
	MarshallingDecoder decoder;

	/**
	 * @param maxFrameLength
	 * @param lengthFieldOffset
	 * @param lengthFieldLength
	 */
	public NettyMessageDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength) {
		super(maxFrameLength, lengthFieldOffset, lengthFieldLength);
		this.decoder = MarshallingDecoder.INSTANCE;
	}

	@Override
	protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
		ByteBuf frame = (ByteBuf) super.decode(ctx, in);
		if (frame == null) {
			return null;
		}
		NettyMessage.NettyMessageBuilder builder = NettyMessage.builder();
		Header.HeaderBuilder headerBuilder = Header.builder().crcCode(frame.readInt()).length(frame.readInt())
				.sessionId(frame.readLong()).type(frame.readByte()).priority(frame.readByte());
		int size = frame.readInt();
		if (size > 0) {
			Map<String, Object> attach = new HashMap<>(size);
			for (int i = 0; i < size; i++) {
				int keySize = frame.readInt();
				byte[] keyArray = new byte[keySize];
				frame.readBytes(keyArray);
				String key = new String(keyArray, "UTF-8");
				attach.put(key, this.decoder.decode(ctx, frame));
			}
			headerBuilder.attachment(attach);
		}
		if (frame.readableBytes() > 4) {
			builder.body(this.decoder.decode(ctx, frame));
		}
		NettyMessage message = builder.header(headerBuilder.build()).build();
		return message;
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		super.exceptionCaught(ctx, cause);
	}

}
