package com.reelbook.core.service.manager.local;

import com.reelbook.core.exception.ManagerException;
import com.reelbook.core.model.BaseModel;

public interface BasePersistenceManager<T extends BaseModel> extends BaseManager<T>
{
	/**
	 */
	public abstract T save(final T model) throws ManagerException;

	/**
	 */
	public abstract T delete(final Long modelID) throws ManagerException;
}