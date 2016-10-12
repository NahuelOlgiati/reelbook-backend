package com.reelbook.core.service.manager.local;

import java.util.List;
import com.reelbook.core.model.BaseModel;

public interface BaseRetrievableManager<T extends BaseModel>
{
	/**
	 */
	public abstract List<T> getList();
}