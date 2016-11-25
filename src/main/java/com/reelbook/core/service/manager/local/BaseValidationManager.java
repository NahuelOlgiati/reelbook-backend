package com.reelbook.core.service.manager.local;

import com.reelbook.core.exception.BaseException;
import com.reelbook.core.model.BaseModel;

public interface BaseValidationManager
{
	public abstract <T extends BaseModel> void valid(T model) throws BaseException;
}