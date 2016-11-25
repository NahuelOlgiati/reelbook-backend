package com.reelbook.rest.endpoint;

import java.util.ArrayList;
import java.util.List;
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
import com.reelbook.core.exception.ManagerException;
import com.reelbook.core.exception.ValidationException;
import com.reelbook.core.service.manager.ejb.BaseEJB;
import com.reelbook.core.util.CompareUtil;
import com.reelbook.model.DocumentType;
import com.reelbook.model.Profile;
import com.reelbook.model.RestSession;
import com.reelbook.model.SystemAgent;
import com.reelbook.model.User;
import com.reelbook.model.embeddable.Document;
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
public class AuthenticationEndPoint extends BaseEJB
{
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
	@ApiResponses(value = {@ApiResponse(code = 405, message = "Invalid input", response = Response.class)})
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response signup(@ApiParam @FormParam("email") String email, @ApiParam @FormParam("username") String username,
			@ApiParam @FormParam("password") String password)
	{
		try
		{
			// final User newUser = getDummyNewUser(email,
			// username, password);
			// userML.save(newUser);
			// em.flush();
			// return Response.ok(newUser).build();
			User full = userML.getFULL(username);
			// return Response.ok(successResponse("CACA")).build();

			List<String> messages = new ArrayList<String>();
			messages.add("PUM1");
			messages.add("PUM2");
			messages.add("PUM3");
			ValidationException v = new ValidationException(messages);
			return ResponseUtil.exceptionMessage(v.getMessages());
		}
		// catch (ManagerException e)
		// {
		// final String errorResponse =
		// errorResponse(e.getMessages().toString());
		// return
		// Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorResponse).type(MediaType.APPLICATION_JSON).build();
		// }
		catch (Exception e)
		{
			return ResponseUtil.fatalException();
		}
	}

	private User getDummyNewUser(String email, String username, String password) throws ManagerException
	{
		final DocumentType documentType = documentTypeML.get(1l);
		final SystemAgent systemAgent = new SystemAgent(new Document(documentType, "212121212121"), username, "caca");
		final SystemAgent systemAgentManaged = systemAgentML.getOrCreate(systemAgent.getDocument(), systemAgent.getFirstName(),
				systemAgent.getLastName());
		final User newUser = new User(username, password, new ArrayList<Profile>());
		newUser.setEmail(email);
		newUser.setSystemAgent(systemAgentManaged);
		return newUser;
	}

	@POST
	@Path("/signin")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response signin(@FormParam("username") String username, @FormParam("password") String password, @Context HttpServletRequest servletRequest)
	{
		try
		{
			User user;
			if ("caca".equals(username))
			{
				user = getDummyUser();
			}
			else
			{
				user = userML.getFULL(username);
				if (CompareUtil.isEmpty(user))
				{
					return ResponseUtil.notFound();
				}
			}

			RestSession restSession = new RestSession(user, null, servletRequest.getHeader("X-FORWARDED-FOR"), Boolean.TRUE);
			restSession = restSessionML.save(restSession);
			user = userML.get(user.getID());
			UserPrincipalMap.put(restSession.getToken(), user);
			String token = restSession.getToken();
			return ResponseUtil.success(token);
		}
		catch (Exception e)
		{
			return ResponseUtil.fatalException();
		}
	}

	private User getDummyUser()
	{
		final User user;
		user = new User("caca", "caca", new ArrayList<Profile>());
		user.setID(1l);
		return user;
	}
}