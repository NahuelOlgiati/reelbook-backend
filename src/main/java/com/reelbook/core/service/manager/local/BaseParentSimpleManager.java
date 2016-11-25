package com.reelbook.core.service.manager.local;

import java.util.List;
import com.reelbook.core.model.BaseParentSimpleModel;
import com.reelbook.core.model.support.QueryHint;

public interface BaseParentSimpleManager<T extends BaseParentSimpleModel<T>> extends BaseSimpleManager<T>
{
	public abstract List<T> getLeaveList(final String description, final QueryHint qh);
}