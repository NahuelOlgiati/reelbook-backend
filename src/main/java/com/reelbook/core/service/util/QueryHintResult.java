package com.reelbook.core.service.util;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class QueryHintResult<T>
{
	private Integer rowCount;
	private List<T> queryList;

	public QueryHintResult(Integer rowCount, List<T> queryList)
	{
		this.rowCount = rowCount;
		this.queryList = queryList;
	}

	public QueryHintResult()
	{
		this(0, new ArrayList<T>());
	}

	public Integer getRowCount()
	{
		return rowCount;
	}

	public void setRowCount(BigInteger rowCount)
	{
		this.rowCount = rowCount.intValue();
	}

	public void setRowCount(Integer rowCount)
	{
		this.rowCount = rowCount;
	}

	public List<T> getQueryList()
	{
		return queryList;
	}

	public void setQueryList(List<T> queryList)
	{
		this.queryList = queryList;
	}

	public void addQueryHintResult(QueryHintResult<T> qHR)
	{
		rowCount = rowCount + qHR.rowCount;
		queryList.addAll(qHR.getQueryList());
	}
}
