package com.reelbook.server.ejb;

import java.util.List;

import com.reelbook.server.model.BaseParentSimpleModel;
import com.reelbook.server.model.support.QueryHint;

public interface BaseParentSimpleManager<T extends BaseParentSimpleModel<T>> extends BaseSimpleManager<T>
{
	/**
	 */
	public abstract List<T> getLeaveList(final String description, final QueryHint qh);
}