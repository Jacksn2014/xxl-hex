# xxl-hex
16进制服务通讯协议

	client : request(ifaceName hex.data)
	
		server:request(ifaceName + hex.data) >> find ifaceName's handler >> response(ifaceName + hex.data)
	
	client:respnse(ifaceName + hex.data)
