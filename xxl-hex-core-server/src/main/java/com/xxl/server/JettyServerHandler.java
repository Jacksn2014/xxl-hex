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
