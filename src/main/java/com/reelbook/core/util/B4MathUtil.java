package com.reelbook.core.util;

import java.math.RoundingMode;

public class B4MathUtil extends MathUtil
{
	private static B4MathUtil me;

	private B4MathUtil()
	{
	}

	public static final synchronized B4MathUtil getMe()
	{
		if (me == null)
		{
			me = new B4MathUtil();
		}
		return me;
	}

	@Override
	public final int getScale()
	{
		return 4;
	}

	@Override
	public final RoundingMode getRoundingMode()
	{
		return RoundingMode.HALF_EVEN;
	}
}