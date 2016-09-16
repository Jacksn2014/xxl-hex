package com.xxl.hex.demo.handler;

import com.xxl.hex.demo.msg.DemoRequest;
import com.xxl.hex.demo.msg.DemoResponse;
import com.xxl.hex.handler.HexHandler;
import com.xxl.hex.handler.annotation.HexHandlerMapping;
import com.xxl.hex.handler.response.HexResponse;
import org.springframework.stereotype.Service;

/**
 * 	HexHandler规定
 *
 * 		1、需要继承HexHandler<T>父类, 并且指定T(Request对象)类型
 * 		2、实现父类的handle方法, 并且定义HexResponse, 开发业务逻辑即可。
 */

@HexHandlerMapping("demohandler")
@Service
public class DemoHandler extends HexHandler<DemoRequest> {

	@Override
	public HexResponse validate(DemoRequest request) {

		if (request.getA()==0 && request.getB()==0) {
			return new HexResponse.SimpleHexResponse(HexResponse.CODE_FAIL, "请输入有意义的参数");
		}

		return null;
	}

	@Override
	public DemoResponse handle(DemoRequest request) {

		int sum = request.getA() + request.getB();

		DemoResponse demoResponse = new DemoResponse();
		demoResponse.setCode(HexResponse.CODE_SUCCESS);
		demoResponse.setSum(sum);
		return demoResponse;
	}

}
