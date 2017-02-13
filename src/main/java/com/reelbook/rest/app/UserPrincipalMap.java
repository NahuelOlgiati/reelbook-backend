package com.reelbook.rest.app;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
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

	public static final UserPrincipal getUserPrincipal(HttpServletRequest req)
	{
		String authorization = req.getHeader(HttpHeaders.AUTHORIZATION);
		String token = authorization.replace("Basic ", "");
		return get(token);
	}

	public static final UserPrincipal getUserPrincipal(MultipartFormDataInput input) throws IOException
	{
		String token = input.getFormDataPart("token", String.class, null);
		return get(token);
	}
}