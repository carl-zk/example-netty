package com.example.aio.chapter7;

import org.msgpack.annotation.Message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 得加@Message注解才能用msgpack
 * 
 * 千万不要忘记加@NoArgsConstructor，不然反序列化工具就报错
 * 
 * @author Carl
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Message
public class UserInfo {
	private String name;
	private int age;

}
