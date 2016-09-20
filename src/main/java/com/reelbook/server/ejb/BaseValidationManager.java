package com.reelbook.server.ejb;

import com.reelbook.core.exception.BaseException;
import com.reelbook.server.model.BaseModel;

public interface BaseValidationManager
{
	/**
	 */
	public abstract <T extends BaseModel> void valid(T model) throws BaseException;
}