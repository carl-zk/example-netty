package com.example.aio.chapter7.msgpack;

import org.msgpack.MessagePack;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 
 * @author Carl
 */
public class MsgpackEncoder extends MessageToByteEncoder<Object> {

	@Override
	protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
		System.out.println("en");
		MessagePack msgPack = new MessagePack(); // not thread-safe
		byte[] raw = msgPack.write(msg);
		out.writeBytes(raw);
	}

}
