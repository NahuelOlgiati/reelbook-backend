package com.reelbook.server.ejb;

import com.reelbook.server.model.BaseRevisionModel;

public interface BaseRevisionManager<T extends BaseRevisionModel>
{
	/**
	 */
	public abstract Class<T> getRevisionModelClass();
}