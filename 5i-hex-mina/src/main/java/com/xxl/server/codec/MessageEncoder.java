package com.xxl.server.codec;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import com.xxl.hex.msg.IResponse;
import com.xxl.hex.util.MsgReflectUtil;

public class MessageEncoder implements ProtocolEncoder {

	/*
	 * 返回消息编码
	 * @see org.apache.mina.filter.codec.ProtocolEncoder#encode(org.apache.mina.core.session.IoSession, java.lang.Object, org.apache.mina.filter.codec.ProtocolEncoderOutput)
	 */
	public void encode(IoSession session, Object message,
			ProtocolEncoderOutput out) throws Exception {
		
		IoBuffer buffer = IoBuffer.allocate(256);
		buffer.setAutoExpand(true);
		buffer.setAutoShrink(true);
		
		IResponse response = (IResponse) message;
		String hex = MsgReflectUtil.responseToHex(response);
		
		buffer.putInt(hex.getBytes().length);
		buffer.put(hex.getBytes());
		
		buffer.flip();
		session.write(buffer);
		//session.close(true);
	}

	public void dispose(IoSession session) throws Exception {

	}

}
