package com.reelbook.core.exception;

import java.util.List;
import javax.ejb.ApplicationException;

@ApplicationException(rollback = true)
@SuppressWarnings("serial")
public final class OperationException extends BaseException
{
	public OperationException(List<String> messages)
	{
		super(messages);
	}

	public OperationException(String message)
	{
		super(message);
	}
}