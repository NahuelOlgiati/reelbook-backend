package com.reelbook.core.rest.response;

public class ModelResponse<T>
{
	private Boolean success;
	private T model;

	public ModelResponse(Boolean success, T model)
	{
		this.success = success;
		this.model = model;
	}

	public Boolean getSuccess()
	{
		return success;
	}

	public void setSuccess(Boolean success)
	{
		this.success = success;
	}

	public T getModel()
	{
		return model;
	}

	public void setModel(T model)
	{
		this.model = model;
	}
}
