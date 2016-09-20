package com.reelbook.rest.util;

import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class ResponseEntity
{
	/**
	 */
	@SuppressWarnings("unchecked")
	public static String success()
	{
		JSONObject response = new JSONObject();
		response.put("success", true);
		return response.toJSONString();
	}
	
	/**
	 */
	@SuppressWarnings("unchecked")
	public static String success(Object body)
	{
		JSONObject response = new JSONObject();
		response.put("success", true);
		response.put("body", body);
		return response.toJSONString();
	}

	/**
	 */
	@SuppressWarnings("unchecked")
	public static String unsuccess(Object body)
	{
		JSONObject response = new JSONObject();
		response.put("success", false);
		response.put("body", body);
		return response.toJSONString();
	}

	/**
	 */
	@SuppressWarnings("unchecked")
	public static String message(List<String> msgs)
	{
		JSONArray messages = new JSONArray();
		for (String msg : msgs)
		{
			messages.add(message(msg));
		}
		return messages.toJSONString();
	}

	/**
	 */
	@SuppressWarnings("unchecked")
	private static JSONObject message(String msg)
	{
		JSONObject message = new JSONObject();
		message.put("severity", "warn");
		message.put("summary", "Warn Message");
		message.put("detail", msg);
		return message;
	}
}
