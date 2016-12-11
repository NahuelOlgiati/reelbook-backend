package com.reelbook.rest.endpoint;

import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.reelbook.rest.app.UserPrincipalMap;
import com.reelbook.rest.util.ResponseUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Stateless
@Path("/session")
@Api(value = "session")
public class RestSessionEndPoint 
{
	@POST
	@Path("/user")
	@ApiOperation(value = "signup", notes = "Retorna usuario en sesion")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response signup(@ApiParam @FormParam("token") String token) {
		try {
			return Response.ok(UserPrincipalMap.get(token)).build();
		} catch (Exception e) {
			return ResponseUtil.fatalException();
		}
	}
}