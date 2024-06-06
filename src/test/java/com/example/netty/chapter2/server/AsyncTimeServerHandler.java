package com.example.netty.chapter2.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.CountDownLatch;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author Carl
 */
@Getter
@Setter
public class AsyncTimeServerHandler implements Runnable {
	private int port;
	CountDownLatch latch;
	AsynchronousServerSocketChannel asynchronousServerSocketChannel;

	/**
	 * @param port
	 */
	public AsyncTimeServerHandler(int port) {
		this.port = port;
		try {
			asynchronousServerSocketChannel = AsynchronousServerSocketChannel.open();
			asynchronousServerSocketChannel.bind(new InetSocketAddress(port));
			System.out.println("The TimeServer is start in port %s".formatted(port));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		latch = new CountDownLatch(1);
		doAccept();
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void doAccept() {
		asynchronousServerSocketChannel.accept(this, new AcceptCompletionHandler());// 收到新请求就回调 AccpetCompletionHandler
	}

}
