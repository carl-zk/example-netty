package com.example.netty.chapter10;

import java.util.List;

import com.example.netty.pojo.HttpXmlResponse;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpResponse;

/**
 * 
 * @author Carl
 * @date 2024年6月11日 22:42:52
 */
public class HttpXmlResponseDecoder extends AbstractHttpXmlDecoder<FullHttpResponse> {

	/**
	 * @param clazz
	 * @param isPrint
	 */
	public HttpXmlResponseDecoder(Class<?> clazz, boolean isPrint) {
		super(clazz, isPrint);
	}

	/**
	 * @param clazz
	 */
	public HttpXmlResponseDecoder(Class<?> clazz) {
		super(clazz);
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, FullHttpResponse msg, List<Object> out) throws Exception {
		HttpXmlResponse response = HttpXmlResponse.builder().httpResponse(msg).result(decode0(ctx, msg.content()))
				.build();
		out.add(response);
	}

}
