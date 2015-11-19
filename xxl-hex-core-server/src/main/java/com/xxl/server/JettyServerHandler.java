package com.xxl.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import com.xxl.demo.msg.response.DemoResponse;
import com.xxl.handler.impl.DemoHandler;
import com.xxl.hex.msg.impl.IRequest;
import com.xxl.hex.serialise.ByteHexConverter;
import com.xxl.hex.serialise.ByteReadFactory;

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

		response.setContentType("text/html;charset=utf-8");
		response.setStatus(HttpServletResponse.SC_OK);
		baseRequest.setHandled(true);
		response.getWriter().println("<h1>HelloWorld</h1>");
		
	}

	public static void main2(String[] args) throws Exception {
		// message receive
		String request_hex = "636F6D2E78786C2E64656D6F2E6D73672E726571756573742E44656D6F5265717565737400000000000000000000000000000000000000000000000000000000E59388E596BDEFBC8CE68891E698AF636C69656E";

		ByteReadFactory reader = new ByteReadFactory(
				ByteHexConverter.hex2Byte(request_hex));
		String ifaceName = reader.readString(IRequest.ifaceName_len);
		System.out.println(ifaceName);

		IRequest msg = (IRequest) Class.forName(ifaceName).newInstance();
		msg.fillHexByte(ByteHexConverter.hex2Byte(request_hex));
		System.out.println(BeanUtils.describe(msg));

		DemoHandler handler = new DemoHandler();
		DemoResponse response = (DemoResponse) handler.handle(msg);

		// message write back
		String response_hex = ByteHexConverter.byte2hex(response.toHexByte());
		System.out.println(response_hex); // C8000000737563636573730000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000007265733AE59388E596BDEFBC8CE68891E698AF63
	}

}
