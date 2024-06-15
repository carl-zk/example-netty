package com.example.netty.chapter12;

import java.io.IOException;

import org.jboss.marshalling.ByteOutput;

import io.netty.buffer.ByteBuf;

/**
 *
 * @author Carl
 * @date 2024年6月14日 21:06:47
 */
class NettyBufByteOutput implements ByteOutput {
	private final ByteBuf buffer;

	/**
	 * Create a new instance which use the given {@link ByteBuf}
	 */
	NettyBufByteOutput(ByteBuf buffer) {
		this.buffer = buffer;
	}

	@Override
	public void close() throws IOException {
		// Nothing to do
	}

	@Override
	public void flush() throws IOException {
		// nothing to do
	}

	@Override
	public void write(int b) throws IOException {
		this.buffer.writeByte(b);
	}

	@Override
	public void write(byte[] bytes) throws IOException {
		this.buffer.writeBytes(bytes);
	}

	@Override
	public void write(byte[] bytes, int srcIndex, int length) throws IOException {
		this.buffer.writeBytes(bytes, srcIndex, length);
	}

	/**
	 * Return the {@link ByteBuf} which contains the written content
	 *
	 */
	ByteBuf getBuffer() {
		return this.buffer;
	}
}
