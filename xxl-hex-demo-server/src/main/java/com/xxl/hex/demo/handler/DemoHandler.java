package com.xxl.hex.demo.handler;

import com.xxl.demo.msg.DemoRequest;
import com.xxl.demo.msg.DemoResponse;
import com.xxl.hex.handler.HexHandler;
import com.xxl.hex.handler.annotation.HexHandlerMapping;
import org.springframework.stereotype.Service;

@HexHandlerMapping(value = "demohandler", requestClass = DemoRequest.class)
@Service
public class DemoHandler extends HexHandler<DemoRequest> {

	@Override
	public DemoResponse handle(DemoRequest request) {
		int sum = request.getA() + request.getB();
		DemoResponse demoResponse = new DemoResponse();
		demoResponse.setSum(sum);
		return demoResponse;
	}

}
