package com.reelbook.core.model.support;

import java.io.Serializable;

@SuppressWarnings("serial")
public final class QueryHint implements Serializable
{
	private final int firstResult;
	private final int maxResults;
	private final boolean countResults;

	/** 
	 */
	public QueryHint(int firstResult, int maxResults, boolean countResults)
	{
		this.firstResult = firstResult;
		this.maxResults = maxResults;
		this.countResults = countResults;
	}

	/**
	 */
	public QueryHint(int firstResult, int maxResults)
	{
		this(firstResult, maxResults, true);
	}

	/**
	 */
	public final int getFirstResult()
	{
		return firstResult;
	}

	/**
	 */
	public final int getMaxResults()
	{
		return maxResults;
	}

	/**
	 */
	public boolean isCountResults()
	{
		return countResults;
	}
}