package com.xxl.hex.demo.handler;

import com.xxl.hex.demo.msg.DemoRequest;
import com.xxl.hex.demo.msg.DemoResponse;
import com.xxl.hex.handler.HexHandler;
import com.xxl.hex.handler.annotation.HexHandlerMapping;
import com.xxl.hex.handler.response.HexResponse;
import org.springframework.stereotype.Service;

@HexHandlerMapping("demohandler")
@Service
public class DemoHandler extends HexHandler<DemoRequest> {

	@Override
	public DemoResponse handle(DemoRequest request) {
		int sum = request.getA() + request.getB();
		DemoResponse demoResponse = new DemoResponse();
		demoResponse.setCode(HexResponse.CODE_SUCCESS);
		demoResponse.setSum(sum);
		return demoResponse;
	}

}
