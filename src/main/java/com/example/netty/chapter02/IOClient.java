package com.example.netty.chapter02;

import java.net.Socket;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 
 * 
 * @author Carl
 */
public class IOClient {
	public static void main(String[] args) throws InterruptedException {
		new Thread(() -> {
			try (Socket socket = new Socket("127.0.0.1", 8000)) {
				while (true) {
					socket.getOutputStream().write((new Date() + ": hello world").getBytes());
					TimeUnit.SECONDS.sleep(2);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}).start();
		Thread.currentThread().join();
	}
}
