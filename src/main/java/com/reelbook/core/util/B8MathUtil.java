package com.reelbook.core.util;

import java.math.RoundingMode;

public final class B8MathUtil extends MathUtil
{
	private static B8MathUtil me;

	private B8MathUtil()
	{
	}

	public static final synchronized B8MathUtil getMe()
	{
		if (me == null)
		{
			me = new B8MathUtil();
		}
		return me;
	}

	@Override
	public final int getScale()
	{
		return 8;
	}

	@Override
	public final RoundingMode getRoundingMode()
	{
		return RoundingMode.HALF_EVEN;
	}
}