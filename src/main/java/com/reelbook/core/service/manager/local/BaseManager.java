package com.reelbook.core.service.manager.local;

import com.reelbook.core.model.BaseModel;

public interface BaseManager<T extends BaseModel>
{
	/**
	 */
	public abstract Class<T> getModelClass();

	/**
	 */
	public abstract T get(final Long modelID);

	/**
	 */
	public abstract T getFULL(final Long modelID);
}