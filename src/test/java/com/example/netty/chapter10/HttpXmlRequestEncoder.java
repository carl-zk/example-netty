package com.example.netty.chapter10;

import static io.netty.handler.codec.http.HttpHeaderNames.ACCEPT;
import static io.netty.handler.codec.http.HttpHeaderNames.ACCEPT_CHARSET;
import static io.netty.handler.codec.http.HttpHeaderNames.ACCEPT_ENCODING;
import static io.netty.handler.codec.http.HttpHeaderNames.ACCEPT_LANGUAGE;
import static io.netty.handler.codec.http.HttpHeaderNames.CONNECTION;
import static io.netty.handler.codec.http.HttpHeaderNames.HOST;
import static io.netty.handler.codec.http.HttpHeaderNames.USER_AGENT;
import static io.netty.handler.codec.http.HttpHeaderValues.CLOSE;
import static io.netty.handler.codec.http.HttpHeaderValues.DEFLATE;
import static io.netty.handler.codec.http.HttpHeaderValues.GZIP;

import java.net.Inet4Address;
import java.util.List;

import com.example.netty.pojo.HttpXmlRequest;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;

/**
 * 
 * @author Carl
 * @date 2024年6月11日 20:02:14
 */
public class HttpXmlRequestEncoder extends AbstractHttpXmlEncoder<HttpXmlRequest> {

	@Override
	protected void encode(ChannelHandlerContext ctx, HttpXmlRequest msg, List<Object> out) throws Exception {
		ByteBuf body = encode0(ctx, msg.getBody());
		FullHttpRequest request = msg.getRequest();
		if (request == null) {
			request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, "/do", body);
			HttpHeaders headers = request.headers();
			headers.set(HOST, Inet4Address.getLocalHost().getHostAddress());
			headers.set(CONNECTION, CLOSE);
			headers.set(ACCEPT_ENCODING, GZIP + "," + DEFLATE);
			headers.set(ACCEPT_CHARSET, "%s,%s;q=0.7,*;q=0.7".formatted(CharsetUtil.ISO_8859_1, CharsetUtil.UTF_8));
			headers.set(ACCEPT_LANGUAGE, "zh");
			headers.set(USER_AGENT, "netty xml http client side");
			headers.set(ACCEPT, "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		}
		HttpUtil.setContentLength(request, body.readableBytes());
		out.add(request);
	}

}
