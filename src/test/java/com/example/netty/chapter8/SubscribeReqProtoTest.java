package com.example.netty.chapter8;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.example.netty.chapter8.pojo.SubscribeReq;
import com.google.protobuf.InvalidProtocolBufferException;

/**
 * 
 * @author Carl
 */
public class SubscribeReqProtoTest {

	private static byte[] encode(SubscribeReq req) {
		return req.toByteArray();
	}

	private static SubscribeReq decode(byte[] body) throws InvalidProtocolBufferException {
		return SubscribeReq.parseFrom(body);
	}

	private static SubscribeReq createSubscribeReq() {
		return SubscribeReq.newBuilder().setSubReqId(1).setUserName("Lily").setProductName("Learn Netty")
				.addAddress("add1").addAddress("add2").build();
	}

	@Test
	void test() {
		SubscribeReq req = createSubscribeReq();
		System.out.println("Before encode:\n%s".formatted(req));
		try {
			SubscribeReq req2 = decode(encode(req));
			System.out.println("After decode:\n%s".formatted(req2));
			assertEquals(req, req2);
		} catch (InvalidProtocolBufferException e) {
			e.printStackTrace();
		}
	}
}
