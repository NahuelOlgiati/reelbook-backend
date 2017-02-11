package com.reelbook.rest.util;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.reelbook.core.service.util.QueryHintResult;

public class ResponseEntity
{
	private static final AnnotationExclusionStrategy exclusionStrategy = new AnnotationExclusionStrategy(); 

	public static String success()
	{
		return getGson().toJson(new ModelResponse(true, null));
	}

	public static String success(Object body)
	{
		return getGson().toJson(new ModelResponse(true, body));
	}

	public static String unsuccess(Object body)
	{
		return getGson().toJson(new ModelResponse(false, body));
	}

	public static String success(QueryHintResult<?> queryHintResult)
	{
		return getGson().toJson(new PagedModelResponse(true, queryHintResult));
	}

	public static String unsuccess(QueryHintResult<?> queryHintResult)
	{
		return getGson().toJson(new PagedModelResponse(false, queryHintResult));
	}

	public static String message(List<String> msgs)
	{
		List<MessageResponse> messages = new ArrayList<MessageResponse>();
		for (String msg : msgs)
		{
			messages.add(new MessageResponse("warn", "Warn Message", msg));
		}
		return getGson().toJson(messages);
	}

	public static String message(String msg)
	{
		return getGson().toJson(new MessageResponse("warn", "Warn Message", msg));
	}

	private static Gson getGson()
	{
		return new GsonBuilder().setExclusionStrategies(exclusionStrategy).create();
	}
}
