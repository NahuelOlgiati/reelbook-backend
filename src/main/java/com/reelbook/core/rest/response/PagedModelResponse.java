package com.reelbook.core.rest.response;

import java.util.ArrayList;
import java.util.List;
import com.reelbook.core.service.util.QueryHintResult;

public class PagedModelResponse<T>
{
	private Boolean success;
	private Integer rowCount;
	private List<T> queryList;

	public PagedModelResponse(Boolean success, QueryHintResult<T> queryHintResult)
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

	public List<T> getQueryList()
	{
		if (queryList == null)
		{
			queryList = new ArrayList<>();
		}
		return queryList;
	}

	public void setQueryList(List<T> queryList)
	{
		this.queryList = queryList;
	}
}
