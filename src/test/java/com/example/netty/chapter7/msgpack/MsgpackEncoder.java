package com.example.netty.chapter7.msgpack;

import org.msgpack.jackson.dataformat.MessagePackMapper;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 
 * @author Carl
 */
public class MsgpackEncoder extends MessageToByteEncoder<Object> {
	ObjectMapper objectMapper = new MessagePackMapper().handleBigIntegerAndBigDecimalAsString();

	@Override
	protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
		System.out.println("en");
		byte[] raw = objectMapper.writeValueAsBytes(msg);
		out.writeBytes(raw);
	}

}
