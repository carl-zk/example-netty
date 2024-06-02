/**
 * all coptyright reserved.
 */
package com.example.netty.chapter02;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author Carl
 */
@Slf4j
public class NIOServer {
	public static void main(String[] args) throws IOException {
		Selector serverSelector = Selector.open();
		Selector clientSelector = Selector.open();
		new Thread(() -> {
			try {
				ServerSocketChannel listenerChannel = ServerSocketChannel.open();
				listenerChannel.socket().bind(new InetSocketAddress(8000));
				listenerChannel.configureBlocking(false);
				listenerChannel.register(serverSelector, SelectionKey.OP_ACCEPT);

				while (true) {
					if (serverSelector.select(1) > 0) {
						Set<SelectionKey> set = serverSelector.selectedKeys();
						Iterator<SelectionKey> keyIterator = set.iterator();
						while (keyIterator.hasNext()) {
							SelectionKey key = keyIterator.next();
							if (key.isAcceptable()) {
								try {
									SocketChannel clientChannel = ((ServerSocketChannel) key.channel()).accept();
									clientChannel.configureBlocking(false);
									clientChannel.register(clientSelector, SelectionKey.OP_READ);
								} finally {
									keyIterator.remove();
								}
							}
						}
					}
				}
			} catch (Exception e) {
			}
		}).start();

		new Thread(() -> {
			try {
				while (true) {
					if (clientSelector.select(1) > 0) {
						Set<SelectionKey> set = clientSelector.selectedKeys();
						Iterator<SelectionKey> keyIterator = set.iterator();

						while (keyIterator.hasNext()) {
							SelectionKey key = keyIterator.next();
							if (key.isReadable()) {
								try {
									SocketChannel clientChannel = (SocketChannel) key.channel();
									ByteBuffer buffer = ByteBuffer.allocate(1024);
									clientChannel.read(buffer);
									buffer.flip();
									log.info(Charset.defaultCharset().newDecoder().decode(buffer).toString());
								} catch (Exception e) {
									keyIterator.remove();
									key.interestOps(SelectionKey.OP_READ);
								}
							}
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}).start();
		log.info("server started");
	}
}
