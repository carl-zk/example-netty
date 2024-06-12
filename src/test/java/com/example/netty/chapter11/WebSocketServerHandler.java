package com.example.netty.chapter11;

import static io.netty.handler.codec.http.HttpResponseStatus.BAD_REQUEST;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocket13FrameDecoder;
import io.netty.handler.codec.http.websocketx.WebSocket13FrameEncoder;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;
import io.netty.util.CharsetUtil;

/**
 *
 * @author Carl
 * @date 2024年6月12日 20:15:59
 */
public class WebSocketServerHandler extends SimpleChannelInboundHandler<Object> {
	public static final Logger logger = LoggerFactory.getLogger(WebSocketServerHandler.class);

	private WebSocketServerHandshaker handshaker;

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
		if (msg instanceof FullHttpRequest) {
			this.handleHttpRequest(ctx, (FullHttpRequest) msg);
		} else if (msg instanceof WebSocketFrame) {
			this.handleWebSocketFrame(ctx, (WebSocketFrame) msg);
		}
	}

	private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest req) {
		if (req.decoderResult().isFailure() || (!"websocket".equals(req.headers().get("Upgrade")))) {
			sendHttpResponse(ctx, req, new DefaultFullHttpResponse(HTTP_1_1, BAD_REQUEST));
			return;
		}
		WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(
				"ws://localhost:8090/websocket", null, false);
		this.handshaker = wsFactory.newHandshaker(req);
		if (this.handshaker == null) {
			WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
		} else {
			this.handshaker.handshake(ctx.channel(), req);
			// this.dynamicBindWebSocketCodec(ctx);
		}
	}

	private static void sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest req, FullHttpResponse resp) {
		if (resp.status().code() != 200) {
			ByteBuf buf = Unpooled.copiedBuffer(resp.status().toString(), CharsetUtil.UTF_8);
			resp.content().writeBytes(buf);
			buf.release();
			HttpUtil.setContentLength(resp, resp.content().readableBytes());
		}

		ChannelFuture f = ctx.channel().writeAndFlush(resp);
		if (HttpUtil.isKeepAlive(req) || resp.status().code() != 200) {
			f.addListener(ChannelFutureListener.CLOSE);
		}
	}

	/**
	 * @deprecated this.handshaker.handshake(ctx.channel(), req) 动态修改pipeline
	 *
	 * @param ctx
	 */
	@Deprecated
	private void dynamicBindWebSocketCodec(ChannelHandlerContext ctx) {
		if (ctx == null) {
			// 会是null吗？？？
			System.out.println("ctx is null");
		}
		ctx.pipeline().replace("wsdecoder", "wsdecoder", new WebSocket13FrameDecoder(true, true, 4096));
		ctx.pipeline().replace("wsencoder", "wsencoder", new WebSocket13FrameEncoder(false));
	}

	private void handleWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) {
		if (frame instanceof CloseWebSocketFrame) {
			this.handshaker.close(ctx.channel(), (CloseWebSocketFrame) frame.retain());
			return;
		}

		if (frame instanceof PingWebSocketFrame) {
			ctx.channel().write(new PongWebSocketFrame(frame.content().retain()));
			return;
		}

		if (!(frame instanceof TextWebSocketFrame)) {
			throw new UnsupportedOperationException(
					"%s frame types not supported".formatted(frame.getClass().getName()));
		}

		String request = ((TextWebSocketFrame) frame).text();
		logger.debug("{} received {}", ctx.channel(), request);
		ctx.channel().write(new TextWebSocketFrame(request + ", welcome learning Netty " + new Date().toString()));
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

}
