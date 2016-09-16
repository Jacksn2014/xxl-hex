package com.xxl.hex.demo;

import com.xxl.hex.serialise.ByteHexConverter;
import com.xxl.hex.serialise.ByteReadFactory;
import com.xxl.hex.serialise.ByteWriteFactory;
import com.xxl.hex.serialise.JacksonUtil;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * 客户端调用示例03: 解析 "XXL-HEX" 数据通信协议, 采用底层最原始方式, 发起API请求 (本实例为 "XXL-HEX" 底层通讯的过程解析模拟, )
 *
 * 特点: 非常灵活, 适用于任何语言, 因为 "byte" 和 "JSON" 是最原始的协议, 覆盖已知任何开发语言, 如 "java开发android或j2ee"、"object-c开发IOS"、"C#开发u3d游戏"……等等, 从而实现, 一次开发, 支持任何语言调用。
 */
public class DemoClientCTest {

	private static final String BASE_URL = "http://localhost:8080/hex";
	private static final String mapping = "demohandler";
	
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {

		// 第一步: "JSON格式-API请求" 初始化 (key值和服务端请求参数属性值保持一致)
		Map<String, Object> requestMap = new HashMap<String, Object>();
		requestMap.put("passphrase", "qwerasdf");
		requestMap.put("a", 1);
		requestMap.put("b", 2);
		String json = JacksonUtil.writeValueAsString(requestMap);

		// 第二步: "JSON格式-API请求" 长度计算 (utf-8格式)
		int len = ByteHexConverter.getByteLen(json);

		// 第三步: "字节数组格式-API请求" 初始化, 组成为: 4个字节(存放"JSON格式-API请求"长度) + len个字节(存放"JSON格式-API请求"数据内容)
		ByteWriteFactory byteWriteFactory = new ByteWriteFactory(4 + len);
		byteWriteFactory.writeInt(len);
		byteWriteFactory.writeString(json, len);
		byte[] bytes = byteWriteFactory.getBytes();

		// 第四步: "字节数组格式-API请求" 转换为 "16进制字符串格式-API请求"
		String request_hex = ByteHexConverter.byte2hex(bytes);

		// 第五步: 封装API接口对应URL地址, 组成为: 服务器地址 + "?mapping=" + API对应Handler + "&hex=" + "16进制字符串格式-API请求";
		String url = BASE_URL + "?mapping=" + mapping + "&hex=" + request_hex;

		// 第六步: 发起Http请求, 请求响应数据都是字符串数据, 即16进制格式数据
		String response_hex = null;
		HttpGet httpGet = new HttpGet(url);
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(3000).setConnectTimeout(3000).build();
			httpGet.setConfig(requestConfig);
			HttpResponse response = httpClient.execute(httpGet);

			HttpEntity entity = response.getEntity();
			if (null != entity) {
				response_hex = EntityUtils.toString(entity, "UTF-8");
				EntityUtils.consume(entity);
				if (response_hex!=null) {
					response_hex = response_hex.trim();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 第七步: "16进制字符串格式-API响应" 转换为 "字节数组格式-API响应"
		byte[] response_bytes = ByteHexConverter.hex2Byte(response_hex);

		// 第八步: "字节数组格式-API响应" 转换为 "JSON格式-API响应"
		ByteReadFactory byteReadFactory = new ByteReadFactory(response_bytes);
		String response_json = byteReadFactory.readString(byteReadFactory.readInt());

		// 第九步: Finish, 可以获取API响应数据, 开发自己的业务了
		System.out.println("json=" + response_json);

	}

}
