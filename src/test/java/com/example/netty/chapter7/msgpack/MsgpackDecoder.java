package com.example.netty.chapter7.msgpack;

import java.util.List;

import org.msgpack.jackson.dataformat.MessagePackMapper;

import com.example.netty.chapter7.UserInfo;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

/**
 * 
 * @author Carl
 */
public class MsgpackDecoder extends MessageToMessageDecoder<ByteBuf> {
	// https://github.com/msgpack/msgpack-java/blob/main/msgpack-jackson/README.md
	ObjectMapper objectMapper = new MessagePackMapper().handleBigIntegerAndBigDecimalAsString();

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
		System.out.println("de");
		final int length = msg.readableBytes();
		final byte[] array = new byte[length];
		msg.getBytes(msg.readerIndex(), array, 0, length);

		out.add(objectMapper.readValue(array, UserInfo.class));
	}

}
