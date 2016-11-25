package com.reelbook.core.service.util;

import javax.persistence.criteria.CriteriaBuilder;

public abstract class ExpressionBuilder
{
	protected final CriteriaBuilder cb;

	protected ExpressionBuilder(CriteriaBuilder cb)
	{
		this.cb = cb;
	}
}