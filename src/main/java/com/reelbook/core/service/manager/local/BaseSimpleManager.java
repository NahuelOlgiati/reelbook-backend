package com.reelbook.core.service.manager.local;

import com.reelbook.core.model.BaseModel;
import com.reelbook.core.model.support.QueryHint;
import com.reelbook.core.service.util.QueryHintResult;

public interface BaseSimpleManager<T extends BaseModel>
{
	public abstract QueryHintResult<T> getQueryHintResult(final String description, final QueryHint queryHint);
}