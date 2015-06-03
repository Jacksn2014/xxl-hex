package com.xxl.pool;

import org.apache.commons.pool.impl.GenericObjectPool;

/**
 * 连接池
 * @author xuxueli
 */
public class ConnectionPool {
	private GenericObjectPool<ConnectionProxy> pool;

	public ConnectionPool(String host, int port) {
		pool = new GenericObjectPool<ConnectionProxy>(new ConnectionFactory(host, port));
		pool.setTestOnBorrow(true);
		pool.setMaxActive(10);
	}

	/**
	 * 获取连接代理
	 * 
	 * @return
	 * @throws Exception
	 */
	public ConnectionProxy getProxy() throws Exception {
		return pool.borrowObject();
	}

	/**
	 * 返还连接代理
	 * 
	 * @param proxy
	 * @throws Exception
	 */
	public void returnProxy(ConnectionProxy proxy) throws Exception {
		pool.returnObject(proxy);
	}

}
