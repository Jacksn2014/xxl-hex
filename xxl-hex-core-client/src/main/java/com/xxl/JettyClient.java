package com.xxl;

import org.eclipse.jetty.client.ContentExchange;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.http.HttpMethods;

public class JettyClient {

	public static String request(String request_hex) {
		String response_hex = null;
		try {
			HttpClient client = new HttpClient();
			client.setConnectorType(HttpClient.CONNECTOR_SELECT_CHANNEL); // 还可以进行其他的配置
			client.start();

			ContentExchange contentExchange = new ContentExchange();
			contentExchange.setMethod(HttpMethods.GET);
			contentExchange.setURL("http://127.0.0.1:8080/?data=" + request_hex);
			contentExchange.setTimeout(30000);

			client.send(contentExchange);
			contentExchange.waitForDone(); // 异步请求在性能上表现的最好，然而有时候我们需要使用同步请求来实现自己的需求

			int status = contentExchange.getResponseStatus();
			if (200 == status) {
				response_hex = contentExchange.getResponseContent().trim();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response_hex;
	}
	
}
