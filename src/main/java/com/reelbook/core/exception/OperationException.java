package com.reelbook.server.exception;

import java.util.List;
import javax.ejb.ApplicationException;

import com.reelbook.core.exception.BaseException;

@ApplicationException(rollback = true)
@SuppressWarnings("serial")
public final class OperationException extends BaseException
{
	/**
	 */
	public OperationException(List<String> messages)
	{
		super(messages);
	}

	/**
	 */
	public OperationException(String message)
	{
		super(message);
	}
}