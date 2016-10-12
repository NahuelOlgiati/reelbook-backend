package com.reelbook.core.exception;

import java.util.List;
import javax.ejb.ApplicationException;

@ApplicationException(rollback = true)
@SuppressWarnings("serial")
public final class ValidationException extends BaseException
{
	/**
	 */
	public ValidationException(List<String> messages)
	{
		super(messages);
	}

	/**
	 */
	public ValidationException(String message)
	{
		super(message);
	}
}