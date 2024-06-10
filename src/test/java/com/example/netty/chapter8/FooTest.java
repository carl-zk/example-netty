package com.example.netty.chapter8;

import org.junit.jupiter.api.Test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.CorruptedFrameException;

/**
 * 
 * @author Carl
 */
public class FooTest {

	@Test
	void test() {
		int value = Integer.MAX_VALUE;
		System.out.println(value);
		System.out.println(Integer.toHexString(value));
		System.out.println(Integer.toBinaryString(value));
		byte mask = (byte) ((1 << 8) - 1);

//		ByteBuf buf = Unpooled.copiedBuffer(new byte[] { (byte) ((num >> 24) & mask), (byte) ((num >> 16)),
//				(byte) ((num >> 8) & mask), (byte) (num & mask) });
		ByteBuf buf = Unpooled.copiedBuffer(
				new byte[] { (byte) (value >>> 24), (byte) (value >>> 16), (byte) (value >>> 8), (byte) value });
		System.out.println(readRawVarint32(buf));
	}

	public static int readRawVarint32(ByteBuf buffer) {
		if (!buffer.isReadable()) {
			return 0;
		}
		buffer.markReaderIndex();
		byte tmp = buffer.readByte();
		if (tmp >= 0) {
			return tmp;
		} else {
			int result = tmp & 127;
			if (!buffer.isReadable()) {
				buffer.resetReaderIndex();
				return 0;
			}
			if ((tmp = buffer.readByte()) >= 0) {
				result |= tmp << 7;
			} else {
				result |= (tmp & 127) << 7;
				if (!buffer.isReadable()) {
					buffer.resetReaderIndex();
					return 0;
				}
				if ((tmp = buffer.readByte()) >= 0) {
					result |= tmp << 14;
				} else {
					result |= (tmp & 127) << 14;
					if (!buffer.isReadable()) {
						buffer.resetReaderIndex();
						return 0;
					}
					if ((tmp = buffer.readByte()) >= 0) {
						result |= tmp << 21;
					} else {
						result |= (tmp & 127) << 21;
						if (!buffer.isReadable()) {
							buffer.resetReaderIndex();
							return 0;
						}
						result |= (tmp = buffer.readByte()) << 28;
						if (tmp < 0) {
							throw new CorruptedFrameException("malformed varint.");
						}
					}
				}
			}
			return result;
		}
	}
}
