package com.reelbook.server.model.mpi;

import com.reelbook.server.model.BaseModel;

public interface Clonable<T extends BaseModel>
{
	/**
	 */
	public abstract T getClone();
}