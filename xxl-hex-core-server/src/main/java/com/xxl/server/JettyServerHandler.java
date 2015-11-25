package com.xxl.server;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import com.xxl.demo.msg.response.DemoResponse;
import com.xxl.hex.msg.impl.IRequest;
import com.xxl.hex.serialise.ByteHexConverter;
import com.xxl.hex.serialise.ByteReadFactory;
import com.xxl.service.impl.DemoHandler;

/**
 * handle为AbstractHandler抽象类中定义的抽象方法，我们对请求的处理操作主要通过此方法来完成，handle中参数包括：
 * target：请求的目标，可以是一个URI或者一个命名dispatcher中的名字。
 * baseRequest：Jetty的可变请求对象，总是unwrapped。
 * Request：不变的请求对象，可能会被过滤器或servlet包装
 * Response：响应，可能会被过滤器或servlet包装
 * hadler设定响应的状态，内容类型，并在使用writer生成响应体之前标记请求为已处理。
 */
public class JettyServerHandler extends AbstractHandler {

	@Override
	public void handle(String target, Request baseRequest,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		// load hex, invoke
		String request_hex = request.getParameter("data");
		String response_hex = invokeService(request_hex);
		
		response.setContentType("text/html;charset=utf-8");
		response.setStatus(HttpServletResponse.SC_OK);
		baseRequest.setHandled(true);
		response.getWriter().println(response_hex);
		
	}

	public String invokeService(String request_hex){
		String response_hex = null;
		
		try {
			// deserialize hex
			ByteReadFactory reader = new ByteReadFactory(ByteHexConverter.hex2Byte(request_hex));
			String ifaceName = reader.readString(IRequest.ifaceName_len);
			
			IRequest hexRequest = (IRequest) Class.forName(ifaceName).newInstance();
			hexRequest.fillHexByte(ByteHexConverter.hex2Byte(request_hex));
			System.out.println(BeanUtils.describe(hexRequest));
			
			// invoke
			DemoHandler handler = new DemoHandler();
			DemoResponse hexResponse = (DemoResponse) handler.handle(hexRequest);
			
			// serialize hex
			response_hex = ByteHexConverter.byte2hex(hexResponse.toHexByte());
			
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		
		return response_hex;
	}

}
