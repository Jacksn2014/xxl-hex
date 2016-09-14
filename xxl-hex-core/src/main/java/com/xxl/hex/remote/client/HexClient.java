package com.xxl.hex.remote.client;

import com.xxl.hex.handler.response.HexResponse;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.MessageFormat;

/**
 * http util to send hex data
 * @author xuxueli
 * @version  2015-11-28 15:30:59
 */
public class HexClient {
	private static Logger logger = LoggerFactory.getLogger(HexClient.class);

	/**
	 * format object to hex-json
	 * @param obj
	 * @return result
	 */
	public static String formatObj2JsonHex(Object obj){
		// obj to json
		String json = JacksonUtil.writeValueAsString(obj);
		int len = ByteHexConverter.getByteLen(json);

		// json to byte[]
		ByteWriteFactory byteWriteFactory = new ByteWriteFactory(4 + len);
		byteWriteFactory.writeInt(len);
		byteWriteFactory.writeString(json, len);
		byte[] bytes = byteWriteFactory.getBytes();

		// byte to hex
		String hex = ByteHexConverter.byte2hex(bytes);
		return hex;
	}

	/**
	 * parse hex-json to object
	 * @param hex
	 * @param clazz
	 * @return result
	 */
	public static <T> T parseJsonHex2Obj(String hex, Class<T> clazz){
		// hex to byte[]
		byte[] bytes = ByteHexConverter.hex2Byte(hex);

		// byte[] to json
		ByteReadFactory byteReadFactory = new ByteReadFactory(bytes);
		String json = byteReadFactory.readString(byteReadFactory.readInt());

		// json to obj
		T obj = JacksonUtil.readValue(json, clazz);
		return obj;
	}

	public static final String MAPPING = "mapping";
	public static final String HEX = "hex";
	public static HexResponse handle(String url, String mapping, Object request, Class<? extends HexResponse> resopnseClass) {

		// format request
		String request_hex = formatObj2JsonHex(request);

		// url parse
		url = MessageFormat.format("{0}?{1}={2}&{3}={4}", url, MAPPING, mapping, HEX, request_hex);

		HttpGet httpGet = new HttpGet(url);
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(3000).setConnectTimeout(3000).build();
			httpGet.setConfig(requestConfig);
			
			HttpResponse response = httpClient.execute(httpGet);
			
			HttpEntity entity = response.getEntity();
			if (null != entity) {
				String response_hex = EntityUtils.toString(entity, "UTF-8");
				EntityUtils.consume(entity);
				if (response_hex!=null) {
					response_hex = response_hex.trim();
					if (response_hex!=null && response_hex.length()>0) {
						HexResponse hexResponse = parseJsonHex2Obj(response_hex, resopnseClass);
						return hexResponse;
					}
				}
			}
		} catch (Exception e) {
			logger.error("", e);

		} finally {
			httpGet.releaseConnection();
			try {
				httpClient.close();
			} catch (IOException e) {
				logger.error("", e);
			}
		}
		return new HexResponse.SimpleHexResponse(HexResponse.SimpleHexResponse.CODE_FAIL, "请求失败");
	}

}
