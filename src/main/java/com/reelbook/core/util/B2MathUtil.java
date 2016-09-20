package com.reelbook.core.util;

import java.math.RoundingMode;

public final class B2MathUtil extends MathUtil
{
	private static B2MathUtil me;

	/**
	 */
	private B2MathUtil()
	{
	}

	/**
	 */
	public static final synchronized B2MathUtil getMe()
	{
		if (me == null)
		{
			me = new B2MathUtil();
		}
		return me;
	}

	/**
	 */
	@Override
	public final int getScale()
	{
		return 2;
	}

	/**
	 */
	@Override
	public final RoundingMode getRoundingMode()
	{
		return RoundingMode.HALF_EVEN;
	}
}