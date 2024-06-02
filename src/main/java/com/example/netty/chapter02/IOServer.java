package com.example.netty.chapter02;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * 
 * @author Carl
 */
@Slf4j
public class IOServer {
	public static void main(String[] args) throws IOException, InterruptedException {
		try (ServerSocket serverSocket = new ServerSocket(8000)) {
			new Thread(() -> {
				while (true) {
					try {
						Socket socket = serverSocket.accept();

						new Thread(() -> {
							try {
								int len;
								byte[] data = new byte[1024];
								InputStream inputStream = socket.getInputStream();
								while ((len = inputStream.read(data)) != -1) {
									log.info(new String(data, 0, len));
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}).start();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}).start();
			log.info("server started");
			Thread.currentThread().join();
		}
	}
}
