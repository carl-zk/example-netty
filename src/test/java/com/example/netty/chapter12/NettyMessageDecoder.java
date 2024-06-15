package com.example.netty.chapter12;

import java.util.HashMap;
import java.util.Map;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 *
 * @author Carl
 * @date 2024年6月14日 22:17:42
 */
@Sharable
public class NettyMessageDecoder extends LengthFieldBasedFrameDecoder {
	MarshallingDecoder decoder;

	/**
	 * @param maxFrameLength
	 * @param lengthFieldOffset
	 * @param lengthFieldLength
	 */
	public NettyMessageDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength) {
		super(maxFrameLength, lengthFieldOffset, lengthFieldLength);
		this.decoder = new MarshallingDecoder();
	}

	@Override
	protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
		ByteBuf frame = (ByteBuf) super.decode(ctx, in);
		if (frame == null) {
			return null;
		}
		NettyMessage.NettyMessageBuilder builder = NettyMessage.builder();
		Header.HeaderBuilder headerBuilder = Header.builder().crcCode(in.readInt()).length(in.readInt())
				.sessionId(in.readLong()).type(in.readByte()).priority(in.readByte());
		int size = in.readInt();
		if (size > 0) {
			Map<String, Object> attach = new HashMap<>(size);
			for (int i = 0; i < size; i++) {
				int keySize = in.readInt();
				byte[] keyArray = new byte[keySize];
				in.readBytes(keyArray);
				String key = new String(keyArray, "UTF-8");
				attach.put(key, this.decoder.decode(ctx, in));
			}
			headerBuilder.attachment(attach);
		}
		if (in.readableBytes() > 4) {
			builder.body(this.decode(ctx, in));
		}
		return builder.header(headerBuilder.build()).build();
	}

}
