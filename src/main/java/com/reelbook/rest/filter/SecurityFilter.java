
package com.reelbook.rest.filter;

import java.io.IOException;
import java.lang.reflect.Method;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.core.ResourceMethodInvoker;

import com.reelbook.core.exception.ValidationException;
import com.reelbook.rest.annotation.RequiredRole;
import com.reelbook.rest.annotation.Secured;
import com.reelbook.rest.app.RoleEnum;
import com.reelbook.rest.app.UserPrincipal;
import com.reelbook.rest.app.UserPrincipalMap;
import com.reelbook.rest.util.ResponseUtil;

@Secured
@Provider
@Priority(Priorities.AUTHORIZATION)
public class SecurityFilter implements ContainerRequestFilter
{
	public static final String RESOURCE_METHOD_INVOKER = "org.jboss.resteasy.core.ResourceMethodInvoker";

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException
	{	
		Response unauthorized = ResponseUtil.exceptionMessage(new ValidationException(Status.UNAUTHORIZED.name()).getMessages());
		String authorization = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
		if (authorization == null || authorization.isEmpty())
		{
			requestContext.abortWith(unauthorized);
			return;
		}

		String token = authorization.replace("Basic ", "");
		UserPrincipal authenticatedUser = UserPrincipalMap.get(token);
		if (authenticatedUser == null)
		{
			requestContext.abortWith(unauthorized);
			return;
		}

		requestContext.setSecurityContext(new SecurityContext()
		{
			@Override
			public boolean isUserInRole(String role)
			{
				return authenticatedUser.getRoles().contains(role);
			}

			@Override
			public boolean isSecure()
			{
				return true;
			}

			@Override
			public Principal getUserPrincipal()
			{
				return authenticatedUser;
			}

			@Override
			public String getAuthenticationScheme()
			{
				return null;
			}
		});

		ResourceMethodInvoker methodInvoker = (ResourceMethodInvoker) requestContext.getProperty(RESOURCE_METHOD_INVOKER);
		Method method = methodInvoker.getMethod();

		if (method.isAnnotationPresent(RequiredRole.class))
		{
			RequiredRole annotation = method.getAnnotation(RequiredRole.class);
			List<RoleEnum> requiredRoles = Arrays.asList(annotation.value());
			for (RoleEnum role : requiredRoles)
			{
				if (!requestContext.getSecurityContext().isUserInRole(role.name()))
				{
					requestContext.abortWith(unauthorized);
					return;
				}
			}
		}
	}
}
