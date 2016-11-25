package com.reelbook.service.msg;

import java.io.Serializable;
import java.text.MessageFormat;
import com.reelbook.core.util.CompareUtil;

@SuppressWarnings("serial")
public class DBSMsgHandler implements Serializable
{
	private static DBSMsgHandler me;
	private static DBBundleList rb;

	private DBSMsgHandler()
	{
	}

	public static final synchronized DBSMsgHandler getMe()
	{
		if (me == null)
		{
			me = new DBSMsgHandler();
			rb = new DBBundleList();
		}
		return me;
	}

	private final DBBundleList getRb()
	{
		return rb;
	}

	public static final synchronized Boolean getLoaded()
	{
		return !CompareUtil.isEmpty(rb);
	}

	public static final synchronized void reload()
	{
		DBSMsgHandler.me = null;
	}

	public final static String getMsg(final String key)
	{
		return getMe().getRb().getString(key);
	}

	public final static String getMsg(final String name, final String key)
	{
		return getMsg(name.concat(".").concat(key));
	}

	public final static String getMsg(final Class<?> c, final String key)
	{
		return getMsg(c.getCanonicalName(), key);
	}

	public final static String getMsg(final Class<?> c, final String key, final Object... parameters)
	{
		return MessageFormat.format(getMsg(c, key), parameters);
	}

	public final static String getMsg(final Enum<?> e)
	{
		return getMsg(e.getClass(), e.name());
	}

	public final static String getMsg(final Enum<?> e, final Object... parameters)
	{
		return MessageFormat.format(getMsg(e.getClass(), e.name()), parameters);
	}
}