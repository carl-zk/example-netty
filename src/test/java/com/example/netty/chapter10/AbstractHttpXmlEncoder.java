package com.example.netty.chapter10;

import java.io.StringWriter;
import java.nio.charset.Charset;

import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IMarshallingContext;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

/**
 * 
 * @author Carl
 * @date 2024年6月11日 20:03:51
 */
public abstract class AbstractHttpXmlEncoder<T> extends MessageToMessageEncoder<T> {
	IBindingFactory factory = null;
	StringWriter writer = null;
	public static final String CHARSET_NAME = "UTF-8";
	public static final Charset UTF_8 = Charset.forName(CHARSET_NAME);

	protected ByteBuf encode0(ChannelHandlerContext ctx, Object body) throws Exception {
		factory = BindingDirectory.getFactory(body.getClass());
		writer = new StringWriter();
		IMarshallingContext mc = factory.createMarshallingContext();
		mc.setIndent(2);
		mc.marshalDocument(body, CHARSET_NAME, null, writer);
		String xmlStr = writer.toString();
		writer.close();
		writer = null;
		ByteBuf buf = Unpooled.copiedBuffer(xmlStr, UTF_8);
		return buf;
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		if (writer != null) {
			writer.close();
			writer = null;
		}
	}

}
