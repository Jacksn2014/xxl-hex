package com.xxl.core.util;

import com.opensymphony.xwork2.ActionContext;
import javax.servlet.http.HttpServletRequest;

public class IpAddressUtil {
	private static String DEFAULT = "127.0.0.1";

	public static int IpToInt(String ip) {
		return toInt(toBytes(ip));
	}

	public static String IntToIp(int addr) {
		return ByteToIp(IntToBytes(addr));
	}

	private static String ByteToIp(byte[] bytes) {
		StringBuilder ipInfo = new StringBuilder();
		ipInfo.append(bytes[0] & 0xFF);
		ipInfo.append(".");
		ipInfo.append(bytes[1] & 0xFF);
		ipInfo.append(".");
		ipInfo.append(bytes[2] & 0xFF);
		ipInfo.append(".");
		ipInfo.append(bytes[3] & 0xFF);
		return ipInfo.toString();
	}

	private static byte[] IntToBytes(int addr) {
		byte[] bytes = new byte[4];
		bytes[0] = ((byte) (addr >>> 24 & 0xFF));
		bytes[1] = ((byte) (addr >>> 16 & 0xFF));
		bytes[2] = ((byte) (addr >>> 8 & 0xFF));
		bytes[3] = ((byte) (addr & 0xFF));

		return bytes;
	}

	private static int toInt(byte[] bytes) {
		int addr = bytes[3] & 0xFF;
		addr |= bytes[2] << 8 & 0xFF00;
		addr |= bytes[1] << 16 & 0xFF0000;
		addr |= bytes[0] << 24 & 0xFF000000;
		return addr;
	}

	private static byte[] toBytes(String ip) {
		String[] ipInfo = ip.split("\\.");
		byte[] results = new byte[4];
		results[0] = ((byte) (Integer.parseInt(ipInfo[0]) & 0xFF));
		results[1] = ((byte) (Integer.parseInt(ipInfo[1]) & 0xFF));
		results[2] = ((byte) (Integer.parseInt(ipInfo[2]) & 0xFF));
		results[3] = ((byte) (Integer.parseInt(ipInfo[3]) & 0xFF));
		return results;
	}

	public static final String getIp() {
		try {
			ActionContext ctx = ActionContext.getContext();
			HttpServletRequest request = (HttpServletRequest) ctx
					.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");

			String ip = request.getHeader("%>a");
			if ((ip == null) || (ip.length() == 0)
					|| ("unknown".equalsIgnoreCase(ip))) {
				ip = request.getHeader("x-forwarded-for");
			}
			if ((ip == null) || (ip.length() == 0)
					|| ("unknown".equalsIgnoreCase(ip))) {
				ip = request.getHeader("Proxy-Client-IP");
			}
			if ((ip == null) || (ip.length() == 0)
					|| ("unknown".equalsIgnoreCase(ip))) {
				ip = request.getHeader("WL-Proxy-Client-IP");
			}
			if ((ip == null) || (ip.length() == 0)
					|| ("unknown".equalsIgnoreCase(ip))) {
				ip = request.getRemoteAddr();
			}
			if ((ip == null) || (ip.length() == 0)
					|| ("unknown".equalsIgnoreCase(ip))) {
				ip = request.getRemoteAddr();
			}
			if ((ip == null) || (ip.length() == 0)
					|| ("unknown".equalsIgnoreCase(ip))) {
				ip = DEFAULT;
			}
			if (ip.split(",").length == 2) {
			}
			return ip.split(",")[0].trim();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return DEFAULT;
	}
}
