package com.example.netty.chapter12;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author Carl
 * @date 2024年6月13日 22:53:16
 */
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Header {
	public static final int CRC_CODE = 0xabef0101;
	private int crcCode;
	/**
	 * 消息长度，包括整个消息头和body
	 */
	private int length;
	private long sessionId;
	/**
	 * <pre>
	 * 0 业务请求
	 * 1 业务响应
	 * 2 业务ONE WAY(both as 0 1)
	 * 3 握手请求
	 * 4 握手响应
	 * 5 心跳请求
	 * 6 心跳响应
	 * </pre>
	 */
	private byte type;
	/**
	 * 0~255
	 */
	private byte priority;
	/**
	 * 可选
	 */
	private Map<String, Object> attachment;// = new HashMap<>();
}
