package com.reelbook.server.model.mpi;

import com.reelbook.server.exception.ValidationException;

public interface Validable
{
	/**
	 */
	public abstract void valid() throws ValidationException;
}