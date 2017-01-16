package com.reelbook.ws.endpoint;

import java.util.logging.Logger;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;


public class MessageEncoder implements Encoder.Text<Message> {

	private static final Logger log = Logger.getLogger("MessageEncoder");

	@Override
	public String encode(Message message) throws EncodeException {
		return message.getJson().toString();
	}

	@Override
	public void init(EndpointConfig config) {
		log.info("Init");
	}

	@Override
	public void destroy() {
		log.info("destroy");
	}

}