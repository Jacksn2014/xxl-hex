package com.xxl.hex.http.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xxl.hex.core.codec.impl.IRequest;
import com.xxl.hex.core.codec.impl.IResponse;
import com.xxl.hex.core.handler.IHandler;
import com.xxl.hex.core.serialise.ByteHexConverter;
import com.xxl.hex.core.serialise.ByteReadFactory;

/**
 * hex servlet
 * @author xuxueli 
 * @version 2015-11-28 13:56:18
 */
public class HexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger = LoggerFactory.getLogger(HexServlet.class);

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		// load hex, invoke
		String request_hex = request.getParameter("data");
		String response_hex = invokeService(request_hex);
				
		response.setContentType("text/html;charset=utf-8");
		response.setStatus(HttpServletResponse.SC_OK);
		response.getWriter().println(response_hex);
	}
	
	
	// registry handlers
	private static Map<String, IHandler> handlerMap = new HashMap<String, IHandler>();
	public static void registryHandler(String requestIfaceName, IHandler handler){
		logger.info(">>>>>>>>>>> registry hex handler, [{}={}]", requestIfaceName, handler);
		handlerMap.put(requestIfaceName, handler);
	}
	private String invokeService(String request_hex){
		String response_hex = null;
		
		try {
			// parse ifaceName
			ByteReadFactory reader = new ByteReadFactory(ByteHexConverter.hex2Byte(request_hex));
			String ifaceName = reader.readString(reader.readInt());
			
			// parse request
			IRequest hexRequest = (IRequest) Class.forName(ifaceName).newInstance();
			hexRequest.fillHexByte(ByteHexConverter.hex2Byte(request_hex));
			
			// find handler
			IHandler handler = handlerMap.get(ifaceName);
			
			// inboke
			IResponse hexResponse = handler.handle(hexRequest);
			
			// serialize hex
			response_hex = ByteHexConverter.byte2hex(hexResponse.toHexByte());
			
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return response_hex;
	}

}
