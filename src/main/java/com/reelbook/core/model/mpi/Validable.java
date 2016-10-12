package com.reelbook.core.model.mpi;

import com.reelbook.core.exception.ValidationException;

public interface Validable
{
	/**
	 */
	public abstract void valid() throws ValidationException;
}