
package com.reelbook.rest.filter;

import java.io.IOException;
import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

@Provider
@Priority(Priorities.AUTHORIZATION + 1)
public class SwaggerFilter implements ContainerRequestFilter
{

	private static final String API_KEY = "caca";

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException
	{
		String path = requestContext.getUriInfo().getAbsolutePath().getPath();
		if (path.contains("swagger.json"))
		{
			String apiKey = requestContext.getUriInfo().getQueryParameters().getFirst("api_key");
			if (!API_KEY.equals(apiKey))
			{
				requestContext.abortWith(Response.status(Status.UNAUTHORIZED).build());
			}
		}
	}
}
