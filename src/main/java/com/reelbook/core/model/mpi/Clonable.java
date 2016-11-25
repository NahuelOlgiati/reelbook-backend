package com.reelbook.core.model.mpi;

import com.reelbook.core.model.BaseModel;

public interface Clonable<T extends BaseModel>
{
	public abstract T getClone();
}