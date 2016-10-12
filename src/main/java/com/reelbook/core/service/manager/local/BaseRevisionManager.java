package com.reelbook.core.service.manager.local;

import com.reelbook.core.model.BaseRevisionModel;

public interface BaseRevisionManager<T extends BaseRevisionModel>
{
	/**
	 */
	public abstract Class<T> getRevisionModelClass();
}