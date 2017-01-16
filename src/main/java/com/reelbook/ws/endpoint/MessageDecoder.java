package com.reelbook.ws.endpoint;

import java.io.StringReader;
import java.util.logging.Logger;

import javax.json.Json;
import javax.json.JsonException;
import javax.json.JsonObject;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;


public class MessageDecoder implements Decoder.Text<Message> {

	private static final Logger log = Logger.getLogger("MessageDecoder");

	@Override
	public Message decode(String string) throws DecodeException {
		JsonObject json = Json.createReader(new StringReader(string)).readObject();
		return new Message(json);
	}

	@Override
	public boolean willDecode(String string) {
		try {
			Json.createReader(new StringReader(string)).read();
			return true;
		} catch (JsonException e) {
			log.info(e.getMessage());
			return false;
		}
	}

	@Override
	public void init(EndpointConfig config) {
		log.info("init");
	}

	@Override
	public void destroy() {
		log.info("destroy");
	}

}
