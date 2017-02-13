package com.reelbook.rest.endpoint;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.reelbook.core.rest.util.ResponseUtil;
import com.reelbook.model.User;
import com.reelbook.rest.app.UserPrincipalMap;
import com.reelbook.service.manager.local.UserManagerLocal;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Stateless
@Path("/session")
@Api(value = "session")
public class RestSessionEndPoint 
{
	@EJB
	private UserManagerLocal userML;
	
	@GET
	@ApiOperation(value = "getSession", notes = "Retorna sesion")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getSession(@Context HttpServletRequest req) {
		try {
			return ResponseUtil.success(UserPrincipalMap.getUserPrincipal(req));
		} catch (Exception e) {
			return ResponseUtil.fatalException();
		}
	}
	
	@POST
	@Path("/user")
	@ApiOperation(value = "getUser", notes = "Retorna usuario en sesion")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response getUser(@ApiParam @FormParam("token") String token) {
		try {
			return ResponseUtil.success(UserPrincipalMap.get(token).getUser());
		} catch (Exception e) {
			return ResponseUtil.fatalException();
		}
	}
	
	@POST
	@Path("/refreshuser")
	@ApiOperation(value = "refreshUser", notes = "Refresca y Retorna usuario en sesion")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response refreshUser(@ApiParam @FormParam("token") String token) {
		try {
			User mapUser = UserPrincipalMap.get(token).getUser();
			mapUser = userML.get(mapUser.getID());
			return ResponseUtil.success(mapUser);	
		} catch (Exception e) {
			return ResponseUtil.fatalException();
		}
	}
}