
package com.xxl.demo.service;

import com.xxl.demo.msg.request.DemoRequest;
import com.xxl.demo.msg.response.DemoResponse;

public interface IDemeService {

	public DemoResponse handle(DemoRequest request);
	
}
