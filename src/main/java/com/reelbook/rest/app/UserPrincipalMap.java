package com.reelbook.rest.app;

import java.util.HashMap;
import java.util.Map;
import com.reelbook.model.User;

public class UserPrincipalMap
{
	private static Map<String, UserPrincipal> userPrincipalMap;

	private static final synchronized Map<String, UserPrincipal> getMe()
	{
		if (userPrincipalMap == null)
		{
			userPrincipalMap = new HashMap<String, UserPrincipal>();
		}
		return userPrincipalMap;
	}

	public static final synchronized void put(String token, User user)
	{
		getMe().put(token, new UserPrincipal(user));
	}

	public static final synchronized UserPrincipal get(String token)
	{
		return getMe().get(token);
	}

	public static final synchronized void remove(String token)
	{
		getMe().remove(token);
	}
}