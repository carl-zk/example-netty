package com.example.netty.chapter12;

import org.jboss.marshalling.Marshaller;
import org.jboss.marshalling.MarshallingConfiguration;
import org.jboss.marshalling.river.RiverMarshallerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.marshalling.MarshallerProvider;
import io.netty.handler.codec.marshalling.ThreadLocalMarshallerProvider;

/**
 *
 * @author Carl
 * @date 2024年6月14日 21:10:07
 */
class MarshallingEncoder {
	public static final MarshallingEncoder INSTANCE = new MarshallingEncoder();
	private static final byte[] LENGTH_PLACEHOLDER = new byte[4];
	MarshallerProvider provider;

	/**
	 *
	 */
	private MarshallingEncoder() {
		this.provider = new ThreadLocalMarshallerProvider(new RiverMarshallerFactory(), new MarshallingConfiguration());
	}

	/**
	 * 向out写入
	 *
	 * <pre>
	 *  ----====================
	 *  [4 bytes of msg bytes length][msg bytes]
	 * </pre>
	 *
	 * @param ctx
	 * @param msg
	 * @param out
	 * @throws Exception
	 */
	protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
		Marshaller marshaller = null;
		try {
			marshaller = this.provider.getMarshaller(ctx);
			int lengthPos = out.writerIndex();
			out.writeBytes(LENGTH_PLACEHOLDER);
			NettyBufByteOutput output = new NettyBufByteOutput(out);
			marshaller.start(output);
			marshaller.writeObject(msg);
			marshaller.finish();
			out.setInt(lengthPos, out.writerIndex() - lengthPos - 4);
		} finally {
			marshaller.close();
		}
	}
}
