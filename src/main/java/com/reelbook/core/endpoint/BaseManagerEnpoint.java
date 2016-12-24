package com.reelbook.core.endpoint;

import javax.ws.rs.core.Response;

import com.reelbook.core.model.BaseModel;

public abstract class BaseManagerEnpoint<T extends BaseModel>
{
	public abstract Response getList();

	public abstract Response get(Long id);

	public abstract Response pagedlist(String description, Integer firstResult, Integer maxResults);

	public abstract Response create(T model);

	public abstract Response update(T model);

	public abstract Response delete(Long id);
}