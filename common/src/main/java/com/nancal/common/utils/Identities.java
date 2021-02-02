package com.nancal.common.utils;

import java.security.SecureRandom;
import java.util.UUID;

/**
 * 封装各种生成唯一性ID算法的工具类.
 * @author zpp
 * @version 2016-02-10
 */
public class Identities {

	private static SecureRandom random = new SecureRandom();

	/**
	 * 封装JDK自带的UUID, 通过Random数字生成, 中间有-分割.
	 * @return
	 */
	public static String uuid() {
		return UUID.randomUUID().toString();
	}

	/**
	 * 封装JDK自带的UUID, 通过Random数字生成, 中间无-分割.
	 * @return
	 */
	public static String uuid2() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	/**
	 * 使用SecureRandom随机生成Long. 
	 * @return
	 */
	public static long randomLong() {
		return Math.abs(random.nextLong());
	}

	/**
	 * 使用SecureRandom随机生成Int. 
	 * @return
	 */
	public static int randomInt() {
		return Math.abs(random.nextInt());
	}
	
	/**
	 * 基于Base62编码的SecureRandom随机生成bytes.
	 * @return
	 */
	public static String randomBase62(int length) {
		byte[] randomBytes = new byte[length];
		random.nextBytes(randomBytes);
		return Encodes.encodeBase62(randomBytes);
	}
}
