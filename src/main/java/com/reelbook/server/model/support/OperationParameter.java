package com.reelbook.server.model.support;

import java.io.Serializable;

import com.reelbook.server.exception.ValidationException;
import com.reelbook.server.model.mpi.Validable;

@SuppressWarnings("serial")
public abstract class OperationParameter implements Validable, Serializable
{
	/**
	 */
	@Override
	public void valid() throws ValidationException
	{
	}
}