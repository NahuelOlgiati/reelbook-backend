package com.reelbook.rest.filter;

import javax.ws.rs.container.ContainerRequestContext;

import javax.ws.rs.container.ContainerResponseContext;

import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.spi.CorsHeaders;

import java.io.IOException;

@Provider
public class CorsFilter implements ContainerResponseFilter {

	public void filter(ContainerRequestContext request, ContainerResponseContext response) throws IOException {
		response.getHeaders().add(CorsHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
		response.getHeaders().add(CorsHeaders.ACCESS_CONTROL_ALLOW_HEADERS,
				"origin, content-type, accept, authorization, x-requested-with");
		response.getHeaders().add(CorsHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
		response.getHeaders().add(CorsHeaders.ACCESS_CONTROL_ALLOW_METHODS, "GET, POST, PUT, DELETE, OPTIONS, HEAD");
		response.getHeaders().add(HttpHeaders.CONTENT_TYPE, "application/json");
		response.getHeaders().add(HttpHeaders.ACCEPT, "application/json");
	}
}