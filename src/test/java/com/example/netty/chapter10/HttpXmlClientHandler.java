package com.example.netty.chapter10;

import com.example.netty.pojo.Address;
import com.example.netty.pojo.Customer;
import com.example.netty.pojo.HttpXmlRequest;
import com.example.netty.pojo.HttpXmlResponse;
import com.example.netty.pojo.Order;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 
 * @author Carl
 * @date 2024年6月11日 23:13:22
 */
public class HttpXmlClientHandler extends SimpleChannelInboundHandler<HttpXmlResponse> {

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		HttpXmlRequest request = HttpXmlRequest.builder()
				.body(Order.builder().orderNumber(123).total(9999.99f)
						.billTo(Address.builder().city("南京").country("中国").postCode("110110").build())
						.customer(Customer.builder().firstName("kate").build()).build())
				.build();
		ctx.writeAndFlush(request).addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, HttpXmlResponse msg) throws Exception {
		System.out.println("receive header:" + msg.getHttpResponse().headers().names());
		System.out.println("receive body is:" + msg.getResult());
	}

}
