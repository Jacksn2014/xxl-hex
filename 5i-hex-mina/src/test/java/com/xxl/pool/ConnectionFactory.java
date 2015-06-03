package com.xxl.pool;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

import org.apache.commons.pool.PoolableObjectFactory;

/**
 * 链接工厂
 * @author xuxueli
 */
public class ConnectionFactory implements PoolableObjectFactory<ConnectionProxy> {
	private String host;
	private int port;

	public ConnectionFactory(String host, int port) {
		this.host = host;
		this.port = port;
	}

	public void activateObject(ConnectionProxy connection) throws Exception {
	}

	/**
	 * 创建连接代理
	 */
	public ConnectionProxy makeObject() throws Exception {
		SocketAddress address = new InetSocketAddress(host, port);
		ConnectionProxy connectionProxy = new ConnectionProxy();
		connectionProxy.createProxy(address);
		return connectionProxy;
	}
	
	/**
	 * 销毁连接代理
	 */
	public void destroyObject(ConnectionProxy connection) throws Exception {
		ConnectionProxy connectionProxy = (ConnectionProxy) connection;
		connectionProxy.close();
	}

	public void passivateObject(ConnectionProxy connection) throws Exception {
	}

	/**
	 * 判断连接代理是否有效
	 */
	public boolean validateObject(ConnectionProxy connection) {
		ConnectionProxy connectionProxy = (ConnectionProxy) connection;
		return connectionProxy.isValidate();
	}

}
