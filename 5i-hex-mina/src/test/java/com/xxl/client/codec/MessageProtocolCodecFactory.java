package com.xxl.client.codec;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

/**
 * 协议编码/解码工厂
 * @author xuxueli
 */
public class MessageProtocolCodecFactory implements ProtocolCodecFactory {
	private ProtocolDecoder decoder;
	private ProtocolEncoder encoder;
	
	public MessageProtocolCodecFactory() {
		this.encoder = new MessageEncoder();
		this.decoder = new MessageDecoder();
	}

	public ProtocolDecoder getDecoder(IoSession session) 
	throws Exception {
		return this.decoder;
	}

	public ProtocolEncoder getEncoder(IoSession session) 
	throws Exception {
		return this.encoder;
	}

}
