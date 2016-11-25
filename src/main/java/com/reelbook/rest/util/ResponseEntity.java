package com.reelbook.rest.util;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import com.reelbook.core.service.util.QueryHintResult;

public class ResponseEntity
{

	private static final Gson gson = new Gson();

	public static String success()
	{
		return gson.toJson(new ModelResponse(true, null));
	}

	public static String success(Object body)
	{
		return gson.toJson(new ModelResponse(true, body));
	}

	public static String unsuccess(Object body)
	{
		return gson.toJson(new ModelResponse(false, body));
	}

	public static String success(QueryHintResult<?> queryHintResult)
	{
		return gson.toJson(new PagedModelResponse(true, queryHintResult));
	}

	public static String unsuccess(QueryHintResult<?> queryHintResult)
	{
		return gson.toJson(new PagedModelResponse(false, queryHintResult));
	}

	public static String message(List<String> msgs)
	{
		List<MessageResponse> messages = new ArrayList<MessageResponse>();
		for (String msg : msgs)
		{
			messages.add(new MessageResponse("warn", "Warn Message", msg));
		}
		return gson.toJson(messages);
	}

	public static String message(String msg)
	{
		return gson.toJson(new MessageResponse("warn", "Warn Message", msg));
	}
}
