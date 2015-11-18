package com.xxl.server;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerCollection;

public class JettyServer {
	
	private Server server;
	private int port;
	public JettyServer(int port) {
		this.port = port;
	}
	
	public void run(){
		try {
			server = new Server(port);
			
			// HandlerList而言，只要有一个Handler将请求标记为已处理，或抛出异常，Handler的调用就到此结束
			// HandlerCollection则不会结束，一直调用到最后一个Handler.setHandled(true)
			HandlerCollection hc =new HandlerCollection();  
		    hc.setHandlers(new Handler[]{new JettyServerHandler()});
		    
			server.setHandler(hc);
			
			server.start();
			server.join();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
