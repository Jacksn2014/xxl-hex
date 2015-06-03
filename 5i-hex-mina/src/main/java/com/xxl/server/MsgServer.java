package com.xxl.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.transport.socket.SocketSessionConfig;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xxl.server.codec.MessageProtocolCodeFactory;
import com.xxl.server.handler.MsgHandler;
import com.xxl.service.ResourceBundle;

public class MsgServer {
	private static transient Logger logger = LoggerFactory.getLogger(MsgServer.class);
	
	private static MsgServer server = new MsgServer();
	public static MsgServer getInstance(){
		return server;
	}
	
	private static NioSocketAcceptor acceptor;
	public void start(){
		if (acceptor != null && acceptor.isActive()) {
			return;
		}
		
		ResourceBundle.getRegistry(); 	// 业务注册器
		
		// 启动服务器
		acceptor = new NioSocketAcceptor();
		acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new MessageProtocolCodeFactory()));
		acceptor.getFilterChain().addLast("threadPool", new ExecutorFilter(Executors.newCachedThreadPool()));
		acceptor.setHandler(new MsgHandler());
		
		SocketSessionConfig config = (SocketSessionConfig) acceptor.getSessionConfig();
		config.setReuseAddress(true);
		config.setTcpNoDelay(true);	// 关闭Negale算法，立即发送数据
		config.setSoLinger(0);		// 立即关闭底层的socket
		config.setReadBufferSize(1024 * 2);
		config.setIdleTime(IdleStatus.BOTH_IDLE, 10);
		
		try {
			acceptor.bind(new InetSocketAddress(10080));
			logger.error("[server is running...]");
		} catch (IOException e) {
			logger.error("[server catch exception : ]", e);
		}
		
	}
	
	public void stop(){
		if (acceptor == null || !acceptor.isActive()) {
			return;
		}
		
		ResourceBundle.dispose();
		
		acceptor.unbind();	// 业务注册器
		acceptor.dispose();
		logger.error("[server is stoped]");
	}
	
}
