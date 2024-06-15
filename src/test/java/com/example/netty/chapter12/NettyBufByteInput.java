package com.example.netty.chapter12;

import java.io.IOException;

import org.jboss.marshalling.ByteInput;

import io.netty.buffer.ByteBuf;

/**
 *
 * @author Carl
 * @date 2024年6月14日 22:29:25
 */
class NettyBufByteInput implements ByteInput {

	private final ByteBuf buffer;

	NettyBufByteInput(ByteBuf buffer) {
		this.buffer = buffer;
	}

	@Override
	public void close() throws IOException {
		// nothing to do
	}

	@Override
	public int available() throws IOException {
		return this.buffer.readableBytes();
	}

	@Override
	public int read() throws IOException {
		if (this.buffer.isReadable()) {
			return this.buffer.readByte() & 0xff;
		}
		return -1;
	}

	@Override
	public int read(byte[] array) throws IOException {
		return this.read(array, 0, array.length);
	}

	@Override
	public int read(byte[] dst, int dstIndex, int length) throws IOException {
		int available = this.available();
		if (available == 0) {
			return -1;
		}

		length = Math.min(available, length);
		this.buffer.readBytes(dst, dstIndex, length);
		return length;
	}

	@Override
	public long skip(long bytes) throws IOException {
		int readable = this.buffer.readableBytes();
		if (readable < bytes) {
			bytes = readable;
		}
		this.buffer.readerIndex((int) (this.buffer.readerIndex() + bytes));
		return bytes;
	}

}
