package com.reelbook.rest.endpoint;

import java.util.ArrayList;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.reelbook.core.exception.ValidationException;
import com.reelbook.core.util.CompareUtil;
import com.reelbook.model.Profile;
import com.reelbook.model.RestSession;
import com.reelbook.model.User;
import com.reelbook.rest.app.UserPrincipalMap;
import com.reelbook.rest.util.ResponseUtil;
import com.reelbook.service.manager.local.RestSessionManagerLocal;
import com.reelbook.service.manager.local.UserManagerLocal;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Stateless
@Path("/authentication")
@Api(value = "authentication")
public class AuthenticationEndPoint
{
	@EJB
	private UserManagerLocal userML;
	@EJB
	private RestSessionManagerLocal restSessionML;

	@POST
	@Path("/signup")
	@ApiOperation(value = "signup", notes = "Crea Cuenta de user")
	@ApiResponses(value = {@ApiResponse(code = 405, message = "Invalid input", response = Response.class)})
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response signup(@ApiParam @FormParam("userName") String userName, @ApiParam @FormParam("firstName") String firstName,
			@ApiParam @FormParam("lastName") String lastName, @ApiParam @FormParam("email") String email,
			@ApiParam @FormParam("password") String password)
	{
		try
		{
			final User newUser = new User(userName, firstName, lastName, password, new ArrayList<Profile>());
			newUser.setEmail(email);
			userML.save(newUser);
			return ResponseUtil.success(newUser);
		}
		catch (Exception e)
		{
			return ResponseUtil.fatalException();
		}
	}

	@POST
	@Path("/signin")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response signin(@FormParam("userName") String userName, @FormParam("password") String password, @Context HttpServletRequest req)
	{
		try
		{
			User user = userML.getFULL(userName);
			if (CompareUtil.isEmpty(user))
			{
				return ResponseUtil.exceptionMessage(new ValidationException("El usuario no existe").getMessages());
			}

			RestSession restSession = new RestSession(user, null, req.getRemoteHost(), Boolean.TRUE);
			restSession = restSessionML.save(restSession);
			user = userML.get(user.getID());
			UserPrincipalMap.put(restSession.getToken(), user);
			return ResponseUtil.success(restSession);
		}
		catch (Exception e)
		{
			return ResponseUtil.fatalException();
		}
	}
}