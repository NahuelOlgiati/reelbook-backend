package com.reelbook.rest.util;

import java.util.Arrays;
import java.util.List;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class ResponseUtil
{
	/**
	 */
	public static Response success()
	{
		return Response.status(Status.OK).entity(ResponseEntity.success()).type(MediaType.APPLICATION_JSON).build();
	}

	/**
	 */
	public static Response success(Object body)
	{
		return Response.status(Status.OK).entity(ResponseEntity.success(body)).type(MediaType.APPLICATION_JSON).build();
	}

	/**
	 */
	public static Response exceptionMessage(String message)
	{
		return Response.status(Status.BAD_REQUEST).entity(ResponseEntity.message(Arrays.asList(message))).type(MediaType.APPLICATION_JSON).build();
	}

	/**
	 */
	public static Response exceptionMessage(List<String> messages)
	{
		return Response.status(Status.BAD_REQUEST).entity(ResponseEntity.message(messages)).type(MediaType.APPLICATION_JSON).build();
	}

	/**
	 */
	public static Response fatalException()
	{
		return Response.status(Status.INTERNAL_SERVER_ERROR).entity(ResponseEntity.unsuccess(Status.INTERNAL_SERVER_ERROR.name()))
				.type(MediaType.APPLICATION_JSON).build();
	}

	/**
	 */
	public static Response unauthorized()
	{
		return Response.status(Status.UNAUTHORIZED).entity(ResponseEntity.unsuccess(Status.UNAUTHORIZED.name())).build();
	}

	/**
	 */
	public static Response notFound()
	{
		return Response.status(Status.NOT_FOUND).entity(ResponseEntity.unsuccess(Status.NOT_FOUND.name())).type(MediaType.APPLICATION_JSON).build();
	}
}