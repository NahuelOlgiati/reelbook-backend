package com.reelbook.rest.endpoint;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.reelbook.core.exception.ManagerException;
import com.reelbook.core.model.support.QueryHint;
import com.reelbook.core.service.manager.ejb.BaseEJB;
import com.reelbook.core.util.CompareUtil;
import com.reelbook.model.User;
import com.reelbook.rest.annotation.RequiredRole;
import com.reelbook.rest.app.RoleEnum;
import com.reelbook.rest.util.ResponseUtil;
import com.reelbook.service.manager.local.UserManagerLocal;

@Stateless
@Path("/user")
public class UserEndPoint extends BaseEJB
{
	@EJB
	private UserManagerLocal userML;

	@GET
	@RequiredRole({RoleEnum.ADMIN})
	@Produces(MediaType.APPLICATION_JSON)
	public User[] get()
	{
		return userML.getQueryHintResult("", new QueryHint(0, Integer.MAX_VALUE)).getQueryList().toArray(new User[0]);
	}

	@GET
	@Path("/get:{id:[0-9][0-9]*}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response findById(@PathParam("id") Long id)
	{
		final User user = userML.getFULL(id);
		if (CompareUtil.isEmpty(user))
		{
			return ResponseUtil.notFound();
		}
		return ResponseUtil.success(user);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response create(User User)
	{
		Response r = null;
		try
		{
			userML.save(User);
		}
		catch (ManagerException e)
		{
			System.out.println("exception in create " + e);
			r = ResponseUtil.fatalException();
		}
		return r;
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response update(User user)
	{
		Response r = null;
		try
		{
			userML.save(user);
			r = ResponseUtil.success();
		}
		catch (ManagerException e)
		{
			System.out.println("exception in update " + e);
			r = ResponseUtil.fatalException();
		}
		return r;
	}
}