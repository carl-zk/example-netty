package com.example.netty.chapter7;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * 千万不要忘记加@NoArgsConstructor，不然反序列化工具就报错
 * 
 * @author Carl
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {
	private String name;
	private int age;

}
