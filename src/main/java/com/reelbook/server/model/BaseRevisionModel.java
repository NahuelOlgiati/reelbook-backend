package com.reelbook.server.model;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public abstract class BaseRevisionModel implements Serializable
{
	/**
	 */
	public abstract Long getID();

	/**
	 */
	public abstract void setID(Long id);

	/**
	 */
	public abstract Date getDate();

	/**
	 */
	public abstract void setDate(Date date);

	/**
	 */
	public abstract String getUserName();

	/**
	 */
	public abstract void setUserName(String userName);
}