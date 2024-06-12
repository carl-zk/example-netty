package com.example.netty.chapter10;

import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpResponseStatus.INTERNAL_SERVER_ERROR;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

import com.example.netty.pojo.Customer;
import com.example.netty.pojo.HttpXmlRequest;
import com.example.netty.pojo.HttpXmlResponse;
import com.example.netty.pojo.Order;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

/**
 * 
 * @author Carl
 * @date 2024年6月11日 23:32:49
 */
public class HttpXmlServerHandler extends SimpleChannelInboundHandler<HttpXmlRequest> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, HttpXmlRequest msg) throws Exception {
		FullHttpRequest request = msg.getRequest();
		Order order = (Order) msg.getBody();
		System.out.println("receive:" + order);
		order.setCustomer(Customer.builder().firstName("lucy").lastName("waka").build());
		ChannelFuture f = ctx.writeAndFlush(HttpXmlResponse.builder().result(order).build());
		f.addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
		if (!HttpUtil.isKeepAlive(request)) {
			f.addListener(new GenericFutureListener<Future<? super Void>>() {

				@Override
				public void operationComplete(Future<? super Void> future) throws Exception {
					ctx.close();
				}
			});
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		if (ctx.channel().isActive()) {
			sendError(ctx, INTERNAL_SERVER_ERROR);
		}
	}

	private void sendError(ChannelHandlerContext ctx, HttpResponseStatus status) {
		FullHttpResponse respose = new DefaultFullHttpResponse(HTTP_1_1, status,
				Unpooled.copiedBuffer("failed:" + status + "\r\n", CharsetUtil.UTF_8));
		respose.headers().set(CONTENT_TYPE, "text/plain; charset=UTF-8");
		ctx.writeAndFlush(respose).addListener(ChannelFutureListener.CLOSE);
	}
}
