package com.xxl.hex.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SignatureUtil {
		private static final String oridinalSuffix = "stpx1!#s0f8p6v5sdq";
		
		/**
		 * sign:md5(msg.byte + key.byte) >>> hex
		 */
		public static String generateSignature(byte[] bytes) {
			byte[] original = new byte[bytes.length + 18];
			System.arraycopy(bytes, 0, original, 0, bytes.length);
			byte[] keys = oridinalSuffix.getBytes();
			for (int index = 0; index < keys.length; index++) {
				original[bytes.length + index] = keys[index];
			}
			
			MessageDigest msgDigest = null;
			try {
				msgDigest = MessageDigest.getInstance("MD5");
				msgDigest.update(original);
			} catch (NoSuchAlgorithmException e) {
				throw new IllegalStateException("System doesn't support MD5 algorithm.");
			}
			
			return new String(HexUtil.byte2hex(msgDigest.digest()));
		}
}

