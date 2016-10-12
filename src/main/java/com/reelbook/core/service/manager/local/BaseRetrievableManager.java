package com.reelbook.server.ejb;

import java.util.List;

import com.reelbook.server.model.BaseModel;

public interface BaseRetrievableManager<T extends BaseModel>
{
	/**
	 */
	public abstract List<T> getList();
}