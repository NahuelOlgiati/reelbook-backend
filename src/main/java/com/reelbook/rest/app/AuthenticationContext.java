package com.reelbook.rest.app;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;

import com.reelbook.model.User;

@ApplicationScoped
public class AuthenticationContext {
	// Token - Principal
	private static final Map<String, UserPrincipal> users;

	static {
		users = new HashMap<String, UserPrincipal>();
	}

	/**
	 */
	public String register(User user) {
		String token = getToken(user);
		users.put(token, new UserPrincipal(user, new Date()));
		return token;
	}

	/**
	 */
	private String getToken(User user) {
		return "CACA";
	}

	/**
	 */
	public UserPrincipal getAuthenticatedUser(String token) {
		return users.get(token);
	}
}