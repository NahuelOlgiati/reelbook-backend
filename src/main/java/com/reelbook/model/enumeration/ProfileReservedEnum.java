package com.reelbook.model.enumeration;

import java.util.ArrayList;
import java.util.List;

public enum ProfileReservedEnum
{
	BASIC,
	ADMIN,
	PORTAL;

	public static final List<String> getNames()
	{
		final List<String> names = new ArrayList<String>();
		for (ProfileReservedEnum value : values())
		{
			names.add(value.name().toLowerCase());
			names.add(value.name());
		}
		return names;
	}
}