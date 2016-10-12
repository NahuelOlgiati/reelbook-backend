package com.reelbook.server.model;

import java.io.Serializable;

import com.reelbook.server.model.mpi.Describable;
import com.reelbook.server.model.mpi.Manageable;
import com.reelbook.server.model.mpi.Validable;

@SuppressWarnings("serial")
public abstract class BaseModel implements Manageable, Validable, Describable, Serializable
{
	/**
	 */
	public abstract Long getID();

	/**
	 */
	public abstract void setID(Long id);

	/**
	 */
	@Override
	public int hashCode()
	{
		return getID().hashCode();
	}

	/**
	 */
	@Override
	public boolean equals(Object to)
	{
		return getID().equals(((BaseModel) to).getID());
	}

	/**
	 */
	@Override
	public boolean isNew()
	{
		return getID().equals(0l);
	}

	/**
	 */
	@Override
	public boolean isPersisted()
	{
		return !getID().equals(0l);
	}

	/**
	 */
	public void initLazyElements()
	{
	}

	/**
	 */
	@Override
	public String getFullDescription()
	{
		return "";
	}
}