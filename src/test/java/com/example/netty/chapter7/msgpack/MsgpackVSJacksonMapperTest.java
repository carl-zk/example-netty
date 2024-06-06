package com.example.netty.chapter7.msgpack;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.msgpack.jackson.dataformat.MessagePackMapper;

import com.example.netty.chapter7.UserInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author Carl
 */
public class MsgpackVSJacksonMapperTest {

	@Test
	void encoding_length_compare() {
		UserInfo userInfo = UserInfo.builder().name("fjdsalfjsa").age(1000).build();
		ObjectMapper jacsonMapper = new ObjectMapper();
		ObjectMapper msgpackMapper = new MessagePackMapper().handleBigIntegerAndBigDecimalAsString();
		try {
			byte[] jacksonBytes = jacsonMapper.writeValueAsBytes(userInfo);
			byte[] msgpackBytes = msgpackMapper.writeValueAsBytes(userInfo);
			System.out
					.println("jacksonBytes:%s vs msgpackBytes:%S".formatted(jacksonBytes.length, msgpackBytes.length));
			assertTrue(jacksonBytes.length > msgpackBytes.length);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
}
