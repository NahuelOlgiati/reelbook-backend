package com.reelbook.app;

import com.reelbook.core.msg.MessageHandler;

public final class MainProperties extends MessageHandler
{
	private static MainProperties me;

	/**
	 */
	private MainProperties()
	{
		super("main");
	}

	/**
	 */
	public static final synchronized MainProperties getMe()
	{
		if (me == null)
		{
			me = new MainProperties();
		}
		return me;
	}
}