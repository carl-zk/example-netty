package com.example.netty.chapter10;

import java.io.StringReader;
import java.nio.charset.Charset;

import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IUnmarshallingContext;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

/**
 * 
 * @author Carl
 * @date 2024年6月11日 22:09:52
 */
public abstract class AbstractHttpXmlDecoder<T> extends MessageToMessageDecoder<T> {
	private IBindingFactory factory;
	private StringReader reader;
	private Class<?> clazz;
	private boolean isPrint;
	public static final String CHARSET_NAME = "UTF-8";
	public static final Charset UTF_8 = Charset.forName(CHARSET_NAME);

	/**
	 * @param clazz
	 */
	protected AbstractHttpXmlDecoder(Class<?> clazz) {
		this(clazz, false);
	}

	/**
	 * @param clazz
	 * @param isPrint
	 */
	protected AbstractHttpXmlDecoder(Class<?> clazz, boolean isPrint) {
		this.clazz = clazz;
		this.isPrint = isPrint;
	}

	protected Object decode0(ChannelHandlerContext ctx, ByteBuf body) throws Exception {
		factory = BindingDirectory.getFactory(clazz);
		String content = body.toString(UTF_8);
		if (isPrint) {
			System.out.println("The body is:" + content);
		}
		reader = new StringReader(content);
		IUnmarshallingContext uc = factory.createUnmarshallingContext();
		Object result = uc.unmarshalDocument(reader);
		reader.close();
		reader = null;
		return result;
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		if (reader != null) {
			reader.close();
			reader = null;
		}
	}

}
