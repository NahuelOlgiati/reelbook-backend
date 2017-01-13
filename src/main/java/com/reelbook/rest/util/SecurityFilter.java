//package com.reelbook.rest.util;
//
//import java.io.IOException;
//import java.lang.reflect.Method;
//import java.security.Principal;
//import java.util.Arrays;
//import java.util.List;
//import javax.annotation.Priority;
//import javax.ejb.EJB;
//import javax.ws.rs.Priorities;
//import javax.ws.rs.container.ContainerRequestContext;
//import javax.ws.rs.container.ContainerRequestFilter;
//import javax.ws.rs.core.HttpHeaders;
//import javax.ws.rs.core.Response;
//import javax.ws.rs.core.Response.Status;
//import javax.ws.rs.core.SecurityContext;
//import javax.ws.rs.ext.Provider;
//import org.jboss.resteasy.core.ResourceMethodInvoker;
//import com.reelbook.core.util.DateUtil;
//import com.reelbook.model.RestSession;
//import com.reelbook.rest.annotation.RequiredRole;
//import com.reelbook.rest.annotation.Secured;
//import com.reelbook.rest.app.RoleEnum;
//import com.reelbook.rest.app.UserPrincipalMap;
//import com.reelbook.service.manager.local.RestSessionManagerLocal;
//import com.tallion.parking.core.model.ApplicationConfiguration;
//import com.tallion.parking.core.service.manager.local.ApplicationConfigurationManagerLocal;
//
//@Secured
//@Provider
//@Priority(Priorities.AUTHORIZATION)
//public class SecurityFilter implements ContainerRequestFilter
//{
//	public static final String RESOURCE_METHOD_INVOKER = "org.jboss.resteasy.core.ResourceMethodInvoker";
//
//	@EJB
//	private RestSessionManagerLocal restSessionML;
//
//	@EJB
//	private ApplicationConfigurationManagerLocal applicationConfigurationML;
//
//	/**
//	 */
//	@Override
//	public void filter(ContainerRequestContext requestContext) throws IOException
//	{
//		String authorization = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
//		if (authorization == null || authorization.isEmpty())
//		{
//			requestContext.abortWith(Response.status(Status.UNAUTHORIZED).build());
//			return;
//		}
//
//		String token = authorization.replace("Basic ", "");
//		RestSession restSession = restSessionML.find(token);
//		if (restSession == null)
//		{
//			requestContext.abortWith(Response.status(Status.UNAUTHORIZED).build());
//			return;
//		}
//
//		if (restSession.getExpires())
//		{
//			// Check-Delete expired session.
//			ApplicationConfiguration aC = applicationConfigurationML.getConfig();
//			if (aC != null)
//			{
//				// Integer
//				if (restSession.getExpires() && DateUtil.getElapsedSeconds(restSession.getLastAccess().getTime()) > aC.getSecondsExpiresRestRegularSession())
//				{
//					requestContext.abortWith(Response.status(Status.UNAUTHORIZED).build());
//					restSessionML.delete(restSession.getID());
//					return;
//				}
//				
//				if (!restSession.getExpires() && DateUtil.getElapsedSeconds(restSession.getLastAccess().getTime()) > aC.getSecondsExpiresRestPatrolSession())
//				{
//					requestContext.abortWith(Response.status(Status.UNAUTHORIZED).build());
//					restSessionML.delete(restSession.getID());
//					return;
//				}
//			}
//		}
//
//		requestContext.setSecurityContext(new SecurityContext()
//		{
//			@Override
//			public boolean isUserInRole(String role)
//			{
//				return UserPrincipalMap.get(token).getRoles().contains(role);
//			}
//
//			@Override
//			public boolean isSecure()
//			{
//				return true;
//			}
//
//			@Override
//			public Principal getUserPrincipal()
//			{
//				return UserPrincipalMap.get(token);
//			}
//
//			@Override
//			public String getAuthenticationScheme()
//			{
//				return null;
//			}
//		});
//
//		ResourceMethodInvoker methodInvoker = (ResourceMethodInvoker) requestContext.getProperty(RESOURCE_METHOD_INVOKER);
//		Method method = methodInvoker.getMethod();
//
//		if (method.isAnnotationPresent(RequiredRole.class))
//		{
//			RequiredRole annotation = method.getAnnotation(RequiredRole.class);
//			List<RoleEnum> requiredRoles = Arrays.asList(annotation.value());
//			for (RoleEnum role : requiredRoles)
//			{
//				if (!requestContext.getSecurityContext().isUserInRole(role.name()))
//				{
//					requestContext.abortWith(Response.status(Status.UNAUTHORIZED).build());
//					return;
//				}
//			}
//		}
//
//		restSession.updateLastAccess();
//		restSessionML.save(restSession);
//	}
//}