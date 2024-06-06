package com.example.netty.chapter2.server;

import org.junit.jupiter.api.Test;

/**
 * 
 * @author Carl
 */
public class TimeServer {
	public static final int PORT = 8080;

	@Test
	void run() throws InterruptedException {
		AsyncTimeServerHandler timeServer = new AsyncTimeServerHandler(PORT);
		new Thread(timeServer, "AIO-AsyncTimeServerHandler-001").start();
		Thread.currentThread().join();
	}

}
