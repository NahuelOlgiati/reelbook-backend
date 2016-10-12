package com.reelbook.core.model;

import com.reelbook.core.exception.ValidationException;
import com.reelbook.core.msg.enumeration.ModelMsgEnum;
import com.reelbook.core.util.CompareUtil;
import com.reelbook.service.msg.DBSMsgHandler;

@SuppressWarnings("serial")
public abstract class BaseSimpleModel extends BaseModel
{
	/**
	 */
	public abstract String getDescription();

	/**
	 */
	public abstract void setDescription(String description);

	/**
	 */
	@Override
	public void valid() throws ValidationException
	{
		if (CompareUtil.isEmpty(getDescription()))
		{
			throw new ValidationException(DBSMsgHandler.getMsg(ModelMsgEnum.INVALID_DESCRIPTION));
		}
	}

	/**
	 */
	@Override
	public String getFullDescription()
	{
		return getDescription();
	}
}