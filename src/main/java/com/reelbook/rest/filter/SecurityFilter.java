package com.reelbook.rest.filter;

import java.io.IOException;
import java.security.Principal;
import javax.annotation.Priority;
import javax.ejb.EJB;
import javax.naming.InitialContext;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;
import com.reelbook.core.exception.ManagerException;
import com.reelbook.core.util.DateUtil;
import com.reelbook.model.RestSession;
import com.reelbook.rest.annotation.Secured;
import com.reelbook.rest.app.ParamApp;
import com.reelbook.rest.app.UserPrincipalMap;
import com.reelbook.service.manager.local.RestSessionManagerLocal;

@Secured
@Provider
@Priority(Priorities.AUTHORIZATION)
public class SecurityFilter implements ContainerRequestFilter
{
	// public static final String RESOURCE_METHOD_INVOKER = "org.jboss.resteasy.core.ResourceMethodInvoker";
	private static final Response unauthorizedResponse = Response.status(Status.UNAUTHORIZED).build();
	private static final Response internalServerErrorResponse = Response.status(Status.INTERNAL_SERVER_ERROR).build();

	@EJB
	private RestSessionManagerLocal restSessionML;

	/**
	 */
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException
	{
		String authorization = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
		if (authorization == null || authorization.isEmpty())
		{
			requestContext.abortWith(unauthorizedResponse);
			return;
		}

		RestSessionManagerLocal restSessionML = null;
		try
		{
			final InitialContext initialContext = new InitialContext();
			restSessionML = (RestSessionManagerLocal) initialContext.lookup("java:global/ejb/RestSessionManagerEJB");
		}
		catch (Exception e)
		{
			requestContext.abortWith(internalServerErrorResponse);
			return;
		}
		finally
		{
			if (restSessionML == null)
			{
				requestContext.abortWith(internalServerErrorResponse);
				return;
			}
		}

		String token = authorization.replace("Basic ", "");
		RestSession restSession = restSessionML.find(token);
		if (restSession == null)
		{
			requestContext.abortWith(unauthorizedResponse);
			return;
		}

		if (restSession.getExpires())
		{
			if (DateUtil.getElapsedSeconds(restSession.getLastAccess().getTime()) > ParamApp.getSecondsExpiresRestRegularSession())
			{
				requestContext.abortWith(unauthorizedResponse);
				try
				{
					restSessionML.delete(restSession.getID());
				}
				catch (ManagerException e)
				{
					requestContext.abortWith(internalServerErrorResponse);
				}
				return;
			}
		}

		requestContext.setSecurityContext(new SecurityContext()
		{
			@Override
			public boolean isUserInRole(String role)
			{
				return UserPrincipalMap.get(token).getRoles().contains(role);
			}

			@Override
			public boolean isSecure()
			{
				return true;
			}

			@Override
			public Principal getUserPrincipal()
			{
				return UserPrincipalMap.get(token);
			}

			@Override
			public String getAuthenticationScheme()
			{
				return null;
			}
		});

		/*
		 * ResourceMethodInvoker methodInvoker = (ResourceMethodInvoker) requestContext.getProperty(RESOURCE_METHOD_INVOKER); Method method
		 * = methodInvoker.getMethod(); if (method.isAnnotationPresent(RequiredRole.class)) { RequiredRole annotation =
		 * method.getAnnotation(RequiredRole.class); List<RoleEnum> requiredRoles = Arrays.asList(annotation.value()); for (RoleEnum role :
		 * requiredRoles) { if (!requestContext.getSecurityContext().isUserInRole(role.name())) {
		 * requestContext.abortWith(Response.status(Status.UNAUTHORIZED).build()); return; } } }
		 */

		try
		{
			restSession.updateLastAccess();
			restSessionML.save(restSession);
		}
		catch (ManagerException e)
		{
			requestContext.abortWith(internalServerErrorResponse);
		}
	}
}