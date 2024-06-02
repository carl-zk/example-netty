package com.example.aio.chapter2.client;

import org.junit.jupiter.api.Test;

import com.example.aio.chapter2.server.TimeServer;

/**
 * 
 * @author Carl
 */
public class TimeClient {

	@Test
	void run() throws InterruptedException {
		AsyncTimeClientHandler client = new AsyncTimeClientHandler("127.0.0.1", TimeServer.PORT);
		client.run();
	}
}
