package com.xxl.hex.demo.handler;

import com.xxl.demo.msg.DemoRequest;
import com.xxl.hex.handler.HexHandler;
import com.xxl.hex.handler.annotation.HexHandlerMapping;
import com.xxl.hex.handler.response.HexResponse;
import org.springframework.stereotype.Service;

@HexHandlerMapping(value = "demohandler", requestClass = DemoRequest.class)
@Service
public class DemoHandler extends HexHandler<DemoRequest> {

	@Override
	public HexResponse.SimpleHexResponse handle(DemoRequest request) {
		int sum = request.getA() + request.getB();
		return new HexResponse.SimpleHexResponse(sum+"");
	}

}
