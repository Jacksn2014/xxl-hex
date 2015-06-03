package com.xxl.client.codec;

import java.nio.charset.Charset;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

public class MessageDecoder extends CumulativeProtocolDecoder {

	/**
	 * 客户端.接收消息.解码
	 */
	protected boolean doDecode(IoSession session, IoBuffer in,
			ProtocolDecoderOutput out) throws Exception {
		if (in.remaining() < 4) {
			return false;
		}
		in.mark();
		int length = in.getInt(in.position());
		if (length > in.remaining() - 4) {
			in.reset();
			return false;
		}

		in.getInt();

		byte[] bytes = new byte[length];
		in.get(bytes, 0, length);

		String info = new String(bytes, Charset.forName("UTF-8").newDecoder().charset());
		out.write(info);
		return true;
	}

}
