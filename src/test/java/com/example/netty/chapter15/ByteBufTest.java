package com.example.netty.chapter15;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.buffer.UnpooledHeapByteBuf;

/**
 *
 * @author Carl
 * @date 2024年6月16日 15:24:34
 */
public class ByteBufTest {

	@Test
	void autoIncr() {
		ByteBuf buf = Unpooled.buffer();
		assertTrue(buf instanceof UnpooledHeapByteBuf);
		assertEquals(1 << 8, buf.capacity());
		byte[] arr = new byte[257];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = 'a';
		}
		buf.writeBytes(arr);
		System.out.println(buf.capacity());
		assertTrue(buf.capacity() > 256);
	}
}
