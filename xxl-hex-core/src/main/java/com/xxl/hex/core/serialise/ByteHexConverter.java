package com.xxl.hex.core.serialise;

import java.math.BigInteger;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

/**
 * hex/byte util
 * @author xuxueli 2015-11-14 22:47:28
 */
public class ByteHexConverter {
	
	/**
	 * byte - to - radix, use BigInteger
	 */
	private static final String hex_tables = "0123456789ABCDEF";
	public static String byte2hex (byte[] iBytes) {
		StringBuilder hex = new StringBuilder(iBytes.length * 2);
		for (int index = 0; index < iBytes.length; index++) {
			hex.append(hex_tables.charAt((iBytes[index] & 0xf0) >> 4));
			hex.append(hex_tables.charAt((iBytes[index] & 0x0f) >> 0));
		}		
		return hex.toString();
	}
	public static byte[] hex2Byte(String hexString) {
		if (hexString == null || hexString.equals("")) {  
	        return null;  
	    }
		byte[] res = new byte[hexString.length() / 2];
		char[] chs = hexString.toCharArray();
		for (int i = 0, c = 0; i < chs.length; i += 2, c++) {
			res[c] = (byte) (Integer.parseInt(new String(chs, i, 2), 16));
		}
		return res;
	}
	
	/**
	 * byte - to - radix, use BigInteger
	 */
	public static final int HEX = 16;
	public static String byte2radix(byte[] iBytes, int radix){
		return new BigInteger(1, iBytes).toString(radix);
	}
	public static byte[] radix2byte(String val, int radix){
		return new BigInteger(val, radix).toByteArray();
	}
	 
	public static void main(String[] args) throws DecoderException {
		// hex - byte[] 方案A：位移
		String temp = "1111111111113d1f3a51sd3f1a32sd1f32as1df2a13sd21f3a2s1df32a13sd2f123s2a3d13fa13sd9999999999";
		System.out.println("明文:" + new String(temp.getBytes()));
		System.out.println("编码:" + byte2hex(temp.getBytes()));
		System.out.println("解码:" + new String(hex2Byte(byte2hex(temp.getBytes()))));
		
		// hex - byte[] 方案B：BigInteger
		System.out.println("编码:" + byte2radix(temp.getBytes(), HEX));
		System.out.println("解码:" + new String(radix2byte(byte2radix(temp.getBytes(), HEX), HEX)));
		
		// hex - byte[] 方案C：common-codec包中Hex工具类
		System.out.println("编码:" + Hex.encodeHexString(temp.getBytes()));
		char[] tmp = Hex.encodeHexString(temp.getBytes()).toCharArray();
		System.out.println("解码:" + new String(Hex.decodeHex(tmp)));
	}
	
}
