package com.reelbook.core.model;

import com.reelbook.core.exception.ValidationException;
import com.reelbook.core.msg.MessageBuilder;
import com.reelbook.core.msg.enumeration.ModelMsgEnum;
import com.reelbook.core.util.CharUtil;
import com.reelbook.core.util.CompareUtil;
import com.reelbook.service.msg.DBSMsgHandler;

@SuppressWarnings("serial")
public abstract class BaseParentSimpleModel<T extends BaseParentSimpleModel<T>> extends BaseSimpleModel
{
	public abstract T getParent();

	public abstract void setParent(T parent);

	@Override
	public void valid() throws ValidationException
	{
		final MessageBuilder mb = new MessageBuilder();

		try
		{
			super.valid();
		}
		catch (ValidationException v)
		{
			mb.addMessage(v.getMessages());
		}

		if (!CompareUtil.isEmpty(getParent()))
		{
			BaseParentSimpleModel<T> p = this;

			while (!CompareUtil.isEmpty(p = p.getParent()))
			{
				if (p.equals(this))
				{
					mb.addMessage(DBSMsgHandler.getMsg(ModelMsgEnum.INVALID_PARENT, p.getDescription()));
				}
			}
		}

		if (!mb.isEmpty())
		{
			throw new ValidationException(mb.getMessages());
		}
	}

	@Override
	public String getFullDescription()
	{
		final StringBuilder sb = new StringBuilder();

		if (!CompareUtil.isEmpty(getDescription()))
		{
			BaseParentSimpleModel<T> p = this;

			while (!CompareUtil.isEmpty(p = p.getParent()))
			{
				sb.insert(0, p.getDescription().concat(CharUtil.getPipeSeparator()));
			}
		}
		return sb.append(getDescription()).toString();
	}
}