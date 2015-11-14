package com.xxl.core.util;

public class HexUtil {
	private static final String hex_tables = "0123456789ABCDEF";
	
	/**
	 * 获取十六进制
	 * @param iBytes
	 * @return
	 */
	public static String getHex(byte[] iBytes) {
		StringBuilder hex = new StringBuilder(iBytes.length * 2);
		for (int index = 0; index < iBytes.length; index++) {
			hex.append(hex_tables.charAt((iBytes[index] & 0xf0) >> 4));
			hex.append(hex_tables.charAt((iBytes[index] & 0x0f) >> 0));
		}		
		return hex.toString();
	}
}
