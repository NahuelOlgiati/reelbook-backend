package com.reelbook.rest.util;

import java.util.ArrayList;
import java.util.List;
import com.reelbook.core.service.util.QueryHintResult;

public class PagedModelResponse
{
	private Boolean success;
	private Integer rowCount;
	private List<?> queryList;

	public PagedModelResponse(Boolean success, QueryHintResult<?> queryHintResult)
	{
		this.success = success;
		this.rowCount = queryHintResult.getRowCount();
		this.queryList = queryHintResult.getQueryList();
	}

	public Boolean getSuccess()
	{
		return success;
	}

	public void setSuccess(Boolean success)
	{
		this.success = success;
	}

	public Integer getRowCount()
	{
		return rowCount;
	}

	public void setRowCount(Integer rowCount)
	{
		this.rowCount = rowCount;
	}

	public List<?> getQueryList()
	{
		if (queryList == null)
		{
			queryList = new ArrayList<>();
		}
		return queryList;
	}

	public void setQueryList(List<?> queryList)
	{
		this.queryList = queryList;
	}
}
