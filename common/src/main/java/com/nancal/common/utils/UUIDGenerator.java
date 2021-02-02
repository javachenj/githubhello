/**
 * 
 */
package com.nancal.common.utils;

import java.util.UUID;

/**
 * @author Junming Wang
 * 
 */
public class UUIDGenerator {
	public static void main(String[] args) {
		String[] ss = getUUID(1);
		for (int i = 0; i < ss.length; i++) {
			System.out.println(ss[i]);
		}
	}

	public static synchronized String getUUID() {
		String s = UUID.randomUUID().toString();
		// 去掉"-"符号
		s=s.replaceAll("-", "");
		return s;
	}

	public static String[] getUUID(int number) {
		if (number < 1) {
			return null;
		}
		String[] ss = new String[number];
		for (int i = 0; i < number; i++) {
			ss[i] = getUUID();
		}
		return ss;
	}

}
