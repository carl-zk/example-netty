package com.example.netty.chapter10;

import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

import java.util.List;

import com.example.netty.pojo.HttpXmlResponse;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpUtil;

/**
 * 
 * @author Carl
 * @date 2024年6月11日 22:32:32
 */
public class HttpXmlResponseEncoder extends AbstractHttpXmlEncoder<HttpXmlResponse> {

	@Override
	protected void encode(ChannelHandlerContext ctx, HttpXmlResponse msg, List<Object> out) throws Exception {
		ByteBuf body = encode0(ctx, msg.getResult());
		FullHttpResponse response = msg.getHttpResponse();
		if (response == null) {
			response = new DefaultFullHttpResponse(HTTP_1_1, OK, body);
		} else {
			response = new DefaultFullHttpResponse(msg.getHttpResponse().protocolVersion(),
					msg.getHttpResponse().status(), body);
		}
		response.headers().set(CONTENT_TYPE, "text/xml");
		HttpUtil.setContentLength(response, body.readableBytes());
		out.add(response);
	}

}
