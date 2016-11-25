package com.reelbook.core.msg;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public abstract class MessageHandler
{
	private ResourceBundle rb;

	protected MessageHandler(String resource)
	{
		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		rb = ResourceBundle.getBundle(resource, Locale.getDefault(), cl);
	}

	public final String getMsg(final String key)
	{
		return rb.getString(key);
	}

	public final String getMsg(final String name, final String key)
	{
		return getMsg(name.concat(".").concat(key));
	}

	public final String getMsg(final Class<?> c, final String key)
	{
		return getMsg(c.getCanonicalName(), key);
	}

	public final String getMsg(final Class<?> c, final String key, final Object... parameters)
	{
		return MessageFormat.format(getMsg(c, key), parameters);
	}

	public final String getMsg(final Enum<?> e)
	{
		return getMsg(e.getClass(), e.name());
	}

	public final String getMsg(final Enum<?> e, final Object... parameters)
	{
		return MessageFormat.format(getMsg(e.getClass(), e.name()), parameters);
	}
}