package com.reelbook.rest.endpoint;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import com.reelbook.core.exception.ManagerException;
import com.reelbook.core.model.support.QueryHint;
import com.reelbook.model.AudioVisual;
import com.reelbook.model.User;
import com.reelbook.rest.annotation.RequiredRole;
import com.reelbook.rest.app.RoleEnum;
import com.reelbook.rest.util.ResponseUtil;
import com.reelbook.service.manager.local.AudioVisualManagerLocal;
import com.reelbook.service.manager.local.FileManagerLocal;
import com.reelbook.service.manager.local.UserManagerLocal;

@Stateless
@Path("/user")
public class UserEndPoint
{
	@EJB
	private UserManagerLocal userML;
	@EJB
	private FileManagerLocal fileML;

	@GET
	@RequiredRole({RoleEnum.ADMIN})
	@Produces(MediaType.APPLICATION_JSON)
	public Response getList()
	{
		Response r = null;
		try
		{
			r = ResponseUtil.success(userML.getQueryHintResult("", new QueryHint(0, Integer.MAX_VALUE)).getQueryList().toArray(new User[0]));
		}
		catch (Exception e)
		{
			System.out.println("exception in create " + e);
			r = ResponseUtil.fatalException();
		}
		return r;
	}

	@GET
	@Path("/get:{id:[0-9][0-9]*}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response get(@PathParam("id") Long id)
	{
		Response r = null;
		try
		{
			r = ResponseUtil.success(userML.get(id));
		}
		catch (Exception e)
		{
			System.out.println("exception in create " + e);
			r = ResponseUtil.fatalException();
		}
		return r;
	}

	@GET
	@Path("/pagedlist:{description}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response pagedlist(@PathParam("description") String description, @DefaultValue("0") @QueryParam("firstResult") Integer firstResult,
			@DefaultValue("10") @QueryParam("maxResults") Integer maxResults)
	{
		Response r = null;
		try
		{
			r = ResponseUtil.success(userML.getQueryHintResult(description, new QueryHint(firstResult, maxResults)));
		}
		catch (Exception e)
		{
			System.out.println("exception in create " + e);
			r = ResponseUtil.fatalException();
		}
		return r;
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response create(User user)
	{
		Response r = null;
		try
		{
			r = ResponseUtil.success(userML.save(user));
		}
		catch (Exception e)
		{
			System.out.println("exception in create " + e);
			r = ResponseUtil.fatalException();
		}
		return r;
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response update(User user)
	{
		Response r = null;
		try
		{
			r = ResponseUtil.success(userML.save(user));
		}
		catch (ManagerException e)
		{
			System.out.println("exception in update " + e);
			r = ResponseUtil.fatalException();
		}
		return r;
	}

	@DELETE
	@Path("/{id:[0-9][0-9]*}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response delete(@PathParam("id") Long id)
	{
		Response r = null;
		try
		{
			r = ResponseUtil.success(userML.delete(id));
		}
		catch (Exception e)
		{
			System.out.println("exception in create " + e);
			r = ResponseUtil.fatalException();
		}
		return r;
	}

	@EJB
	private AudioVisualManagerLocal audioVisualML;

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/stream")
	public StreamingOutput stream()
	{
		StreamingOutput r = null;
		try
		{
			AudioVisual audioVisual = audioVisualML.get(1l);
			ByteArrayInputStream bis = new ByteArrayInputStream(audioVisual.getVideo().getContent());

			StreamingOutput stream = new StreamingOutput()
			{
				@Override
				public void write(OutputStream output) throws IOException, WebApplicationException
				{
					Writer writer = new BufferedWriter(new OutputStreamWriter(output));

					int b;
					while ((b = bis.read()) != -1)
					{
						writer.write(b);

					}
					writer.flush();
				}
			};
			r = stream;
		}
		catch (Exception e)
		{
			System.out.println("exception in create " + e);
			r = null;
		}
		return r;
	}
}