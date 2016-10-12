package com.reelbook.server.ejb;

import com.reelbook.server.model.BaseModel;
import com.reelbook.server.model.support.QueryHint;
import com.reelbook.server.util.QueryHintResult;

public interface BaseSimpleManager<T extends BaseModel>
{
	/**
	 */
	public abstract QueryHintResult<T> getQueryHintResult(final String description, final QueryHint queryHint);
}