package com.reelbook.core.model;

import java.io.Serializable;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.reelbook.core.model.mpi.Describable;
import com.reelbook.core.model.mpi.Manageable;
import com.reelbook.core.model.mpi.Validable;

@SuppressWarnings("serial")
public abstract class BaseModel implements Manageable, Validable, Describable, Serializable, ExclusionStrategy
{
	public abstract Long getID();

	public abstract void setID(Long id);

	@Override
	public int hashCode()
	{
		return getID().hashCode();
	}

	@Override
	public boolean equals(Object to)
	{
		return getID().equals(((BaseModel) to).getID());
	}

	@Override
	public boolean isNew()
	{
		return getID().equals(0l);
	}

	@Override
	public boolean isPersisted()
	{
		return !getID().equals(0l);
	}

	public void initLazyElements()
	{
	}

	@Override
	public String getFullDescription()
	{
		return "";
	}

	@Override
	public boolean shouldSkipField(FieldAttributes f)
	{
		return f.getName().equals("youtubeCredential");
	}

	@Override
	public boolean shouldSkipClass(Class<?> clazz)
	{
		return false;
	}
}