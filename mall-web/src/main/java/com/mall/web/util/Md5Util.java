package com.mall.web.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Arrays;

public final class Md5Util {
	public static String getMD5(String str) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] input = str.getBytes("utf-8");
			byte[] m = md.digest(input); //加密完成了
			BigInteger big = new BigInteger(1, m);
			return big.toString(16);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("MD5加密出现错误");
		}
	}
}
