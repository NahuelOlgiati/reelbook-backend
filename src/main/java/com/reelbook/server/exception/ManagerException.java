package com.reelbook.server.exception;

import java.util.List;
import javax.ejb.ApplicationException;

import com.reelbook.core.exception.BaseException;

@ApplicationException(rollback = true)
@SuppressWarnings("serial")
public final class ManagerException extends BaseException
{
	/**
	 */
	public ManagerException(List<String> messages)
	{
		super(messages);
	}

	/**
	 */
	public ManagerException(String message)
	{
		super(message);
	}
}