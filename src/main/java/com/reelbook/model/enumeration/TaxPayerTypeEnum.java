package com.reelbook.model.enumeration;

import com.reelbook.server.model.mpi.LabeledValued;
import com.reelbook.service.msg.DBSMsgHandler;

public enum TaxPayerTypeEnum implements LabeledValued<TaxPayerTypeEnum>
{
	ALL,
	NATURAL,
	LEGAL;

	/**
	 */
	@Override
	public String getLabel()
	{
		return DBSMsgHandler.getMsg(this);
	}

	/**
	 */
	@Override
	public TaxPayerTypeEnum getValue()
	{
		return this;
	}

	/**
	 */
	public Boolean getIsAll()
	{
		return this.equals(ALL);
	}

	/**
	 */
	public Boolean getIsNatural()
	{
		return this.equals(NATURAL);
	}

	/**
	 */
	public Boolean getIsNaturalOrAll()
	{
		return this.equals(NATURAL) || this.equals(ALL);
	}

	/**
	 */
	public Boolean getIsLegal()
	{
		return this.equals(LEGAL);
	}

	/**
	 */
	public Boolean getIsLegalOrAll()
	{
		return this.equals(LEGAL) || this.equals(ALL);
	}
}