package com.example.netty.chapter12;

import org.jboss.marshalling.ByteInput;
import org.jboss.marshalling.MarshallingConfiguration;
import org.jboss.marshalling.Unmarshaller;
import org.jboss.marshalling.river.RiverMarshallerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.marshalling.ThreadLocalUnmarshallerProvider;
import io.netty.handler.codec.marshalling.UnmarshallerProvider;

/**
 *
 * @author Carl
 * @date 2024年6月14日 22:19:28
 */
class MarshallingDecoder {
	UnmarshallerProvider provider;

	/**
	 *
	 */
	public MarshallingDecoder() {
		this.provider = new ThreadLocalUnmarshallerProvider(new RiverMarshallerFactory(),
				new MarshallingConfiguration());
	}

	protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
		int objectSize = in.readInt();
		ByteBuf buf = in.slice(in.readerIndex(), objectSize);
		ByteInput input = new NettyBufByteInput(buf);
		Unmarshaller unmarshaller = null;
		try {
			unmarshaller = this.provider.getUnmarshaller(ctx);
			unmarshaller.start(input);
			Object object = unmarshaller.readObject();
			unmarshaller.finish();
			in.readerIndex(in.readerIndex() + objectSize);
			return object;
		} finally {
			unmarshaller.close();
		}
	}
}
