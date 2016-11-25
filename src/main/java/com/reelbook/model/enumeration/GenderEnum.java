package com.reelbook.model.enumeration;

import com.reelbook.core.model.mpi.LabeledValued;
import com.reelbook.service.msg.DBSMsgHandler;

public enum GenderEnum implements LabeledValued<GenderEnum>
{
	MALE,
	FEMALE;

	@Override
	public String getLabel()
	{
		return DBSMsgHandler.getMsg(this);
	}

	@Override
	public GenderEnum getValue()
	{
		return this;
	}
}