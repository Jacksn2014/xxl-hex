package com.xxl.server;

import org.eclipse.jetty.server.Server;

public class JettyServer {
	
	private Server server;
	private int port;
	public JettyServer(int port) {
		this.port = port;
	}
	
	public void run(){
		try {
			server = new Server(port);
			server.setHandler(new JettyServerHandler());
			server.start();
			server.join();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
