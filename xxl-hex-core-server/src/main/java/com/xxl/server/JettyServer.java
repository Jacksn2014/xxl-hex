package com.xxl.server;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.util.thread.ExecutorThreadPool;

public class JettyServer {
	
	private Server server;
	private int port;
	public JettyServer(int port) {
		this.port = port;
	}
	
	public void run(){
		try {
			// Jetty的Service对象就是Jetty容器，实例化出这样一个对象就产生了一个容器
			server = new Server();	
			server.setThreadPool(new ExecutorThreadPool(200, 200, 30000));	// 可以为Server设置线程池，如果不设置Server处理请求默认为阻塞状态
			
			// 创建Server实例时传入了一个端口号参数，程序内部会创建一个Connector的默认实例，在指定的端口上监听请求。然而，通常嵌入式的Jetty需要为一个Server实例显式地实例化并配置一个或多个Connector。
			SelectChannelConnector connector = new SelectChannelConnector();
			connector.setPort(port);			//设置端口号
			connector.setMaxIdleTime(30000);	// 设置最大空闲时间，最大空闲时间在以下几种情况下应用:1)  等待一个连接的新的请求时；2)  读取请求的头信息和内容时；3)   写响应的头信息和内容时
			server.setConnectors(new Connector[] { connector });	// 设置这个Server的连接（可以是多个），每个连接有对应的线程池和Handler
			
			// HandlerList而言，只要有一个Handler将请求标记为已处理，或抛出异常，Handler的调用就到此结束;HandlerCollection则不会结束，一直调用到最后一个Handler.setHandled(true)
			HandlerCollection handlerCollection =new HandlerCollection();  
			handlerCollection.setHandlers(new Handler[]{new JettyServerHandler()});
			server.setHandler(handlerCollection);
			
			server.start();
			server.join();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
