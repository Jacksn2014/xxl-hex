package com.xxl.client.codec;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

public class MessageEncoder implements ProtocolEncoder {

	/**
	 * 客户端.发送消息.编码
	 */
	public void encode(IoSession session, Object message,
			ProtocolEncoderOutput out) throws Exception {
		String msgInfo = (String) message;
		IoBuffer buffer = IoBuffer.allocate(1024);
		buffer.setAutoExpand(true);
		buffer.setAutoShrink(true);

		buffer.putInt(msgInfo.getBytes().length + 4);
		buffer.putInt(msgInfo.getBytes().length);
		buffer.put(msgInfo.getBytes());
		buffer.flip();
		out.write(buffer);
	}

	@Override
	public void dispose(IoSession session) throws Exception {
	}

}
