package com.xxl.server;

public class Server {
	
	public static void main(String[] args) {
		new JettyServer(8080).run();
	}
	
}
