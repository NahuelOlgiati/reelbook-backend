package com.reelbook.model.enumeration;

import com.reelbook.core.model.mpi.LabeledValued;
import com.reelbook.service.msg.DBSMsgHandler;

public enum PhoneTypeEnum implements LabeledValued<PhoneTypeEnum> {
	HOME, MOBILE, OFFICE, FAX, OTHER;

	/**
	 */
	@Override
	public String getLabel() {
		return DBSMsgHandler.getMsg(this);
	}

	/**
	 */
	@Override
	public PhoneTypeEnum getValue() {
		return this;
	}
}