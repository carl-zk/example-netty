package com.example.aio.chapter2.server;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * 
 * @author Carl
 */
public class AcceptCompletionHandler implements CompletionHandler<AsynchronousSocketChannel, AsyncTimeServerHandler> {

	@Override
	public void completed(AsynchronousSocketChannel result, AsyncTimeServerHandler attachment) {
		/**
		 * 因为一个AsyncTimeServerHandler实例是无状态的，所以可以重复注册，保证下个请求也能处理
		 */
		attachment.asynchronousServerSocketChannel.accept(attachment, this); // 处理之前，先注册一下，为后一个做准备

		ByteBuffer buffer = ByteBuffer.allocate(1024); // 1M
		result.read(buffer, buffer, new ReadCompletionHandler(result));// 读到buffer后就回调ReadCompletionHandler
	}

	@Override
	public void failed(Throwable exc, AsyncTimeServerHandler attachment) {
		attachment.latch.countDown();
	}

}
