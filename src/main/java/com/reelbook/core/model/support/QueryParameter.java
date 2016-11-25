package com.reelbook.core.model.support;

import java.io.Serializable;
import com.reelbook.core.exception.ValidationException;
import com.reelbook.core.model.mpi.Validable;

@SuppressWarnings("serial")
public abstract class QueryParameter implements Validable, Serializable
{
	@Override
	public void valid() throws ValidationException
	{
	}
}