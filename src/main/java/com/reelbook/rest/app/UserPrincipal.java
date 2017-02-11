package com.reelbook.rest.app;

import java.security.Principal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.reelbook.model.Permit;
import com.reelbook.model.Profile;
import com.reelbook.model.User;

public class UserPrincipal implements Principal, ExclusionStrategy
{
	private User user;
	private Set<String> roles;
	private Date signinDate;

	public UserPrincipal(User user)
	{
		this.user = user;
		this.roles = new HashSet<String>();
		for (Profile profile : user.getProfiles())
		{
			for (Permit permit : profile.getPermits())
			{
				this.roles.add(permit.getCode());
			}
		}
		this.signinDate = new Date();
	}

	public User getUser()
	{
		return user;
	}

	public Set<String> getRoles()
	{
		return roles;
	}

	public Date getSigninDate()
	{
		return signinDate;
	}

	@Override
	public String getName()
	{
		return getUser().getUserName();
	}
	
	@Override
	public boolean shouldSkipField(FieldAttributes f)
	{
//		f.getAnnotation(annotation) null if it has not
		return f.getName().equals("youtubeCredential");
	}

	@Override
	public boolean shouldSkipClass(Class<?> arg0) 
	{
		return false;
	}
}