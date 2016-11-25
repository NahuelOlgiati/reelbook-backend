package com.reelbook.model.enumeration;

import com.reelbook.core.model.mpi.LabeledValued;
import com.reelbook.service.msg.DBSMsgHandler;

public enum ContactTypeEnum implements LabeledValued<ContactTypeEnum>
{
	MAIL,
	SMS,
	OTHER;

	@Override
	public String getLabel()
	{
		return DBSMsgHandler.getMsg(this);
	}

	@Override
	public ContactTypeEnum getValue()
	{
		return this;
	}
}