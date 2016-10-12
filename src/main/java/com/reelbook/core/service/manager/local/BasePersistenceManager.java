package com.reelbook.server.ejb;

import com.reelbook.server.exception.ManagerException;
import com.reelbook.server.model.BaseModel;

public interface BasePersistenceManager<T extends BaseModel> extends BaseManager<T>
{
	/**
	 */
	public abstract T save(final T model) throws ManagerException;

	/**
	 */
	public abstract T delete(final Long modelID) throws ManagerException;
}