package com.reelbook.ws.endpoint;

import java.io.StringWriter;

import javax.json.Json;
import javax.json.JsonObject;

/**
 * Message.
 *
 * @author Guido.Oelmann
 */
public class Message {

	private JsonObject json;

	public Message(JsonObject json) {
		this.json = json;
	}

	public JsonObject getJson() {
		return json;
	}

	public void setJson(JsonObject json) {
		this.json = json;
	}

	@Override
	public String toString() {
		StringWriter writer = new StringWriter();
		Json.createWriter(writer).write(json);
		return writer.toString();
	}

}