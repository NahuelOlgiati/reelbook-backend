package com.reelbook.core.msg;

import java.util.ArrayList;
import java.util.List;

public final class MessageBuilder
{
	private List<String> messages;

	/**
	 */
	public MessageBuilder()
	{
		this.messages = new ArrayList<String>();
	}

	/**
	 */
	public final List<String> getMessages()
	{
		return messages;
	}

	/**
	 */
	public final void addMessage(final String message)
	{
		getMessages().add(message);
	}

	/**
	 */
	public final void addMessage(final List<String> messages)
	{
		getMessages().addAll(messages);
	}

	/**
	 */
	public final boolean isEmpty()
	{
		return getMessages().isEmpty();
	}
}