package com.xxl.server.codec;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

/**
 * 消息协议编码工厂
 * @author xuxueli
 *
 */
public class MessageProtocolCodeFactory implements ProtocolCodecFactory {
	private ProtocolEncoder encoder;
	private ProtocolDecoder decoder;
	
	public MessageProtocolCodeFactory() {
		this.encoder = new MessageEncoder();
		this.decoder = new MessageDecoder();
	}
	
	/*
	 * 
	 * @see org.apache.mina.filter.codec.ProtocolCodecFactory#getEncoder(org.apache.mina.core.session.IoSession)
	 */
	public ProtocolEncoder getEncoder(IoSession session) 
	throws Exception {
		return this.encoder;
	}

	/*
	 * 
	 * @see org.apache.mina.filter.codec.ProtocolCodecFactory#getDecoder(org.apache.mina.core.session.IoSession)
	 */
	public ProtocolDecoder getDecoder(IoSession session) 
	throws Exception {
		return this.decoder;
	}

}
