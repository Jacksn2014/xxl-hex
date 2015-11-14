package com.xxl.core.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 通过 properties 文件配置设置常量基类 负责加载和读取 properties 属性文件并提供访问的静态工具方法
 */

public class PropertiesUtil {
	protected static Log logger = LogFactory.getLog(PropertiesUtil.class);
	protected static Properties p = new Properties();

	public static void init(String propertyFileName) {
		InputStream in = null;
		try {
			in = PropertiesUtil.class.getClassLoader().getResourceAsStream(propertyFileName);
			if (in != null) {
				p.load(in);
			}
		} catch (IOException e) {
			logger.error("load " + propertyFileName + " into Constants error!");
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					logger.error("close " + propertyFileName + " error!");
				}
			}
		}
	}

	/**
	 * 获取String类型配置
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static String getProperty(String key, String defaultValue) {
		return p.getProperty(key, defaultValue);
	}

	/**
	 * 获取int类型配置
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	protected static int getProperty(String key, int defaultValue) {
		try {
			return Integer.parseInt(getProperty(key, ""));
		} catch (Exception e) {
			return defaultValue;
		}
	}

	
	public static void main(String[] args) {
		init("memcached.properties");
		System.out.println(getProperty("server.address", "no"));
	}

}
