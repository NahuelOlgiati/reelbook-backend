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

import com.reelbook.core.service.manager.ejb.BaseEJB;
import com.reelbook.core.util.CompareUtil;
import com.reelbook.model.Profile;
import com.reelbook.model.RestSession;
import com.reelbook.model.User;
import com.reelbook.rest.app.UserPrincipalMap;
import com.reelbook.rest.util.ResponseUtil;
import com.reelbook.service.manager.local.DocumentTypeManagerLocal;
import com.reelbook.service.manager.local.NaturalTaxPayerManagerLocal;
import com.reelbook.service.manager.local.RestSessionManagerLocal;
import com.reelbook.service.manager.local.SystemAgentManagerLocal;
import com.reelbook.service.manager.local.UserManagerLocal;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Stateless
@Path("/authentication")
@Api(value = "authentication")
public class AuthenticationEndPoint extends BaseEJB {
	@EJB
	private NaturalTaxPayerManagerLocal naturalTaxPayerML;
	@EJB
	private DocumentTypeManagerLocal documentTypeML;
	@EJB
	private SystemAgentManagerLocal systemAgentML;
	@EJB
	private UserManagerLocal userML;
	@EJB
	private RestSessionManagerLocal restSessionML;

	@POST
	@Path("/signup")
	@ApiOperation(value = "signup", notes = "Crea Cuenta de user")
	@ApiResponses(value = { @ApiResponse(code = 405, message = "Invalid input", response = Response.class) })
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response signup(@ApiParam @FormParam("email") String email, @ApiParam @FormParam("userName") String userName,
			@ApiParam @FormParam("password") String password) {
		try {
			final User newUser = new User(userName, password, new ArrayList<Profile>());
			userML.save(newUser);
			em.flush();
			return Response.ok(newUser).build();
		} catch (Exception e) {
			return ResponseUtil.fatalException();
		}
	}

	@POST
	@Path("/signin")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response signin(@FormParam("userName") String userName, @FormParam("password") String password,
			@Context HttpServletRequest servletRequest) {
		try {
			User user;
			if ("caca".equals(userName)) {
				user = getDummyUser();
			} else {
				user = userML.getFULL(userName);
				if (CompareUtil.isEmpty(user)) {
					return ResponseUtil.notFound();
				}
			}

			RestSession restSession = new RestSession(user, null, servletRequest.getHeader("X-FORWARDED-FOR"),
					Boolean.TRUE);
			restSession = restSessionML.save(restSession);
			user = userML.get(user.getID());
			UserPrincipalMap.put(restSession.getToken(), user);
			String token = restSession.getToken();
			return ResponseUtil.success(token);
		} catch (Exception e) {
			return ResponseUtil.fatalException();
		}
	}

	// TODO remove
	private User getDummyUser() {
		final User user;
		user = new User("caca", "caca", new ArrayList<Profile>());
		user.setID(1l);
		return user;
	}
}