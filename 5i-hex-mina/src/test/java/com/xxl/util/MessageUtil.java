package com.xxl.util;

import org.apache.mina.core.session.IoSession;

import com.xxl.pool.ConnectionPool;
import com.xxl.pool.ConnectionProxy;

/**
 * client消息工具
 * @author xuxueli
 */
public class MessageUtil {
	// mina 连接池
	private static final ConnectionPool pool = new ConnectionPool("127.0.0.1", 10080);
	
	/**
	 * 发送消息
	 * @param messageInfo
	 */
	public static void sendMessage(String messageInfo) {
		ConnectionProxy connection = null;
		try {
			connection = pool.getProxy();
			IoSession session = connection.getSession();
			session.write(messageInfo);
		} catch (Exception e) {			
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					pool.returnProxy(connection);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}			
		}
	}
	
}
