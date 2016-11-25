package com.reelbook.service.manager.support;

import com.reelbook.core.model.support.QueryParameter;

@SuppressWarnings("serial")
public class UserQP extends QueryParameter
{
	private final String firstName;
	private final String lastName;
	private final String userName;
	private final String email;
	private final Boolean active;

	public UserQP(String firstName, String lastName, String userName, String email, Boolean active)
	{
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.userName = userName;
		this.email = email;
		this.active = active;
	}

	public final String getFirstName()
	{
		return firstName;
	}

	public final String getLastName()
	{
		return lastName;
	}

	public final String getUserName()
	{
		return userName;
	}

	public final String getEmail()
	{
		return email;
	}

	public final Boolean getActive()
	{
		return active;
	}
}