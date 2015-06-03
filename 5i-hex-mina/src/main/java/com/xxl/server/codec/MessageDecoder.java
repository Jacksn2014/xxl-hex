package com.xxl.server.codec;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xxl.core.exception.ExceptionKey;
import com.xxl.core.exception.WebException;
import com.xxl.hex.msg.IRequest;
import com.xxl.hex.util.MsgReflectUtil;

public class MessageDecoder extends CumulativeProtocolDecoder {
	private static transient Logger logger = LoggerFactory
			.getLogger(MessageDecoder.class);

	/*
	 * 输入消息解码
	 * @see org.apache.mina.filter.codec.CumulativeProtocolDecoder#doDecode(org.apache.mina.core.session.IoSession, org.apache.mina.core.buffer.IoBuffer, org.apache.mina.filter.codec.ProtocolDecoderOutput)
	 */
	protected boolean doDecode(IoSession session, IoBuffer in,
			ProtocolDecoderOutput out) throws Exception {

		// 报文前4个字节为消息体的长度
		if (in.remaining() < 4) {
			return false;
		}
		in.mark();
		int length = in.getInt();
		if (length < in.remaining()) {
			return false;
		}

		int ilen = in.getInt();
		byte[] dataInfos = new byte[ilen];
		in.get(dataInfos, 0, ilen);

		// 请求数据IoBuffer转String
		String hex = new String(dataInfos);	// String.valueOf(byte[] param); 方式获取的是内存地址,shit

		// 解析16进制消息
		IRequest msgInfo = MsgReflectUtil.hexToRequest(hex);
		if (msgInfo == null) {
			logger.error("msgInfo is null, hex = ", hex);
			throw new WebException(ExceptionKey.defaultKey, "请求参数异常");
		}

		// 校验签名
		String paramSignature = msgInfo.getSignature();
		String generateSignature = MsgReflectUtil.requestToSignature(msgInfo);
		if (!paramSignature.equals(generateSignature)) {
			logger.info("hexToRequest signature not equal, paramSignature : {} generateSignature : {}",
					new Object[] { paramSignature, generateSignature });
			throw new WebException(ExceptionKey.defaultKey, "加密串校验异常");
		}

		out.write(msgInfo);
		return true;
	}

}
