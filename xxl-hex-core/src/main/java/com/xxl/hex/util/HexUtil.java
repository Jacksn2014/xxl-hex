package com.xxl.hex.util;

/**
 * hex/byte util
 * @author xuxueli 2015-11-14 22:47:28
 */
public class HexUtil {
	private static final String hex_tables = "0123456789ABCDEF";
	
	/**
	 * byte 2 hex
	 */
	public static String byte2hex (byte[] iBytes) {
		StringBuilder hex = new StringBuilder(iBytes.length * 2);
		for (int index = 0; index < iBytes.length; index++) {
			hex.append(hex_tables.charAt((iBytes[index] & 0xf0) >> 4));
			hex.append(hex_tables.charAt((iBytes[index] & 0x0f) >> 0));
		}		
		return hex.toString();
	}
	
	/**
	 * hex 2 byte
	 */
	public static byte[] hex2byte(String hexString) {  
	    if (hexString == null || hexString.equals("")) {  
	        return null;  
	    }  
	    hexString = hexString.toUpperCase();  
	    int length = hexString.length() / 2;  
	    char[] hexChars = hexString.toCharArray();  
	    byte[] d = new byte[length];  
	    for (int i = 0; i < length; i++) {  
	        int pos = i * 2;  
	        d[i] = (byte) ((byte) hex_tables.indexOf(hexChars[pos]) << 4 | hex_tables.indexOf(hexChars[pos + 1]));  
	    }  
	    return d;  
	}  
	
	public static void main(String[] args) {
		String temp = "asdfasdf";
		System.out.println(new String(temp.getBytes()));
		System.out.println(new String(hex2byte(byte2hex(temp.getBytes()))));
	}
	
}
