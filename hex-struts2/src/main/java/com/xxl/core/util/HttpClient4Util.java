package com.xxl.core.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

/**
 * HTTP请求Client
 * @author xuxueli
 */
public class HttpClient4Util {
	
	/**
	 * HTTP.GET请求
	 * @param url			请求地址
	 * @param queryString	请求参数
	 * @return
	 */
	public static String httpGet(String reqURL, String queryString){
		String responseContent = null;
		// 处理GET请求URL
		if (queryString != null && !queryString.equals("")) {
			reqURL = reqURL + "?" + queryString;
		}
		HttpGet httpGet = new HttpGet(reqURL);
		// 执行请求
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try{
			HttpResponse response = httpClient.execute(httpGet);
			// 解析请求
			HttpEntity entity = response.getEntity();							// 响应实体
			//response.getStatusLine().getStatusCode();							// 响应状态
			if(null != entity){
				//long responseLength = entity.getContentLength();				//响应长度
				responseContent = EntityUtils.toString(entity, "UTF-8");		// 响应内容
				EntityUtils.consume(entity);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			httpGet.releaseConnection();
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return responseContent;
	}
	
	/**
	 * HTTP.POST请求
	 * @param url			请求地址
	 * @param queryString	请求参数
	 * @return
	 */
	public static String httpPost(String reqURL, String queryString){
		String responseContent = null;
		// 封装POST参数
		HttpPost httpPost = new HttpPost(reqURL);
		httpPost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded");
		if (queryString != null && !queryString.equals("")) {
			httpPost.setEntity(new StringEntity(queryString, "UTF-8"));
		}
		// 执行请求
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try{
			HttpResponse response = httpClient.execute(httpPost);
			// 解析请求
			HttpEntity entity = response.getEntity();
			if (null != entity) {
				responseContent = EntityUtils.toString(entity, "UTF-8");
				EntityUtils.consume(entity);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			httpPost.releaseConnection();
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return responseContent;
	}
	
	/**
	 * HTTP.POST请求
	 * @param url		请求地址
	 * @param params	请求参数
	 * @return
	 */
	public static String sendPostRequest(String reqURL, Map<String, String> params){
		String responseContent = null;
		// 封装POST参数 1/2
		HttpPost httpPost = new HttpPost(reqURL);
		// 执行请求
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try{
			// 封装POST参数 2/2
			if (params != null && !params.isEmpty()) {
				List<NameValuePair> formParams = new ArrayList<NameValuePair>();	//创建参数队列
				for(Map.Entry<String,String> entry : params.entrySet()){
					formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
				}
				httpPost.setEntity(new UrlEncodedFormEntity(formParams, "UTF-8"));
			}
			HttpResponse response = httpClient.execute(httpPost);
			// 解析请求
			HttpEntity entity = response.getEntity();
			if (null != entity) {
				responseContent = EntityUtils.toString(entity, "UTF-8");
				EntityUtils.consume(entity);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			httpPost.releaseConnection();
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return responseContent;
	}
	
}