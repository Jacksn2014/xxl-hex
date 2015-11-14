package com.xxl.action.hex.util;

import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestStreamFactory {
	private static transient Logger logger = LoggerFactory.getLogger(RequestStreamFactory.class);
	private int m_iPos;
	private int m_iReqLen;
	private byte[] m_byte = null;
	
	
	/**
	 * 解析十六进制请求头
	 * @param hex
	 * @return
	 */
	public boolean readRequestHex(String hex) {
		if (hex == null || hex.trim().length() == 0 || hex.trim().length() % 2 != 0) {
			return false;
		}
		
		m_iPos = 0;
		m_iReqLen = hex.length() / 2;		
		
		m_byte = new byte[m_iReqLen];
		for (int index = 0; index < m_iReqLen; index++) {
			m_byte[index] = (byte) (0xff & Integer.parseInt(hex.substring(index * 2, index * 2 + 2), 16));
		}
		
		return true;
	}
	
	/**
	 * 获取int
	 * @return
	 */
	public int readInt() {
		if (m_iPos + 4 > m_iReqLen) {
			return 0;
		}
		int iInt = (m_byte[m_iPos] & 0xff) 
				| ((m_byte[m_iPos + 1] & 0xff) << 8)
				| ((m_byte[m_iPos + 2] & 0xff) << 16)
				| ((m_byte[m_iPos + 3] & 0xff) << 24);		
		m_iPos += 4;
		return iInt;
	}
	
	/**
	 * 获取long
	 * @return
	 */
	public long readLong() {
		if (m_iPos + 8 > m_iReqLen) {
			return 0;
		}
		long iLong = (m_byte[m_iPos] & 0xff) 
				| ((m_byte[m_iPos + 1] & 0xff) << 8)
				| ((m_byte[m_iPos + 2] & 0xff) << 16)
				| ((m_byte[m_iPos + 3] & 0xff) << 24)
				| ((m_byte[m_iPos + 4] & 0xff) << 32)
				| ((m_byte[m_iPos + 5] & 0xff) << 40)
				| ((m_byte[m_iPos + 6] & 0xff) << 48)
				| ((m_byte[m_iPos + 7] & 0xff) << 56);
		m_iPos += 8;
		return iLong;
	}
	
	/**
	 * 获取string
	 * @param iByteSize
	 * @return
	 */
	public String readString(int iByteSize) {
		if (m_iPos + iByteSize > m_iReqLen) {
			logger.error("[byte stream factory read string length error.]");
			return "";
		}
		
		int index = 0;
		for (index = 0; index < iByteSize; index++) {
			if (m_byte[m_iPos + index] == 0) {
				break;
			}
		}
		String msg = "";
		try {
			msg = new String(m_byte, m_iPos, index, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error("[byte stream factory read string exception.]", e);
		}
		m_iPos += iByteSize;
		
		return msg;
	}
	
}
