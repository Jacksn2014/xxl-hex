package com.xxl.pool;

import java.net.SocketAddress;
import java.util.concurrent.TimeUnit;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.logging.LogLevel;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.DefaultSocketSessionConfig;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xxl.client.codec.MessageProtocolCodecFactory;
import com.xxl.client.handler.MessageClientHandler;
import com.xxl.core.exception.WebException;

/**
 * 连接代理
 * @author xuxueli
 */
public class ConnectionProxy {
	private IoSession session;
	private NioSocketConnector connector;
	private static transient Logger logger = LoggerFactory.getLogger(ConnectionProxy.class);
	
	/**
	 * 创建连接代理类
	 * @param configuration
	 * @param address
	 * @throws UICException
	 */
	public void createProxy(SocketAddress address) 
	throws WebException {
		this.connector = new NioSocketConnector();
		
		LoggingFilter filter = new LoggingFilter();
		filter.setMessageReceivedLogLevel(LogLevel.DEBUG);
		
		connector.getFilterChain().addLast("logger", filter);
		connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new MessageProtocolCodecFactory()));
		connector.setConnectTimeoutMillis(5000);
		
		DefaultSocketSessionConfig sessionConfiguration = (DefaultSocketSessionConfig) this.connector.getSessionConfig();
		sessionConfiguration.setReadBufferSize(1024);
		sessionConfiguration.setSendBufferSize(512);
		sessionConfiguration.setReuseAddress(true);
		sessionConfiguration.setTcpNoDelay(true);
		sessionConfiguration.setKeepAlive(true);
		sessionConfiguration.setSoLinger(-1);
		sessionConfiguration.setWriteTimeout(5);
		
		connector.setHandler(new MessageClientHandler());
		
		ConnectFuture future = this.connector.connect(address);
		future.awaitUninterruptibly(5, TimeUnit.SECONDS);
		
		if (!future.isConnected()) {
			logger.error("pook social connect to {} failed.", address);
			this.connector.dispose();
			this.connector = null;
			throw new WebException("IO", "网络异常");
		}
		
		logger.info("connect to {} succeed.", address);
		this.session = future.getSession();
	}
	
	/**
	 * 获取连接
	 * @return
	 */
	public IoSession getSession() {
		return this.session;
	}
	
	/**
	 * 是否有效
	 * @return
	 */
	public boolean isValidate() {
		if (this.session != null && this.connector != null) {
			return this.connector.isActive() && this.session.isConnected();
		}
		return false;
	}
	
	/**
	 * 关闭连接
	 */
	public void close() {
		if (this.session != null) {
			if (this.session.isConnected()) {
				session.getCloseFuture().awaitUninterruptibly();
			}
			this.session.close(true);
			this.session = null;
		}
		if (this.connector != null) {
			this.connector.dispose();
			this.connector = null;
		}
		logger.info("connect close.");
	}
}
