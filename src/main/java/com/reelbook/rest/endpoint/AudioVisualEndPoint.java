package com.reelbook.rest.endpoint;

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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.reelbook.core.endpoint.BaseManagerEnpoint;
import com.reelbook.core.exception.ManagerException;
import com.reelbook.model.AudioVisual;
import com.reelbook.rest.util.ResponseUtil;
import com.reelbook.service.manager.local.AudioVisualManagerLocal;
import com.reelbook.service.manager.local.FileManagerLocal;

@Stateless
@Path("/audiovisual")
public class AudioVisualEndPoint extends BaseManagerEnpoint<AudioVisual>
{
	@EJB
	private AudioVisualManagerLocal audioVisualML;
	@EJB
	private FileManagerLocal fileML;

//	@GET
//	@RequiredRole({RoleEnum.ADMIN})
//	@Produces(MediaType.APPLICATION_JSON)
	public Response getList()
	{
		Response r = null;
//		try
//		{
//			r = ResponseUtil.success(audioVisualML.getQueryHintResult("", new QueryHint(0, Integer.MAX_VALUE)).getQueryList().toArray(new AudioVisual[0]));
//		}
//		catch (Exception e)
//		{
//			System.out.println("exception in create " + e);
//			r = ResponseUtil.fatalException();
//		}
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
			r = ResponseUtil.success(audioVisualML.get(id));
		}
		catch (Exception e)
		{
			System.out.println("exception in create " + e);
			r = ResponseUtil.fatalException();
		}
		return r;
	}

//	@Override
//	@GET
//	@Path("/pagedlist:{description}")
//	@Produces(MediaType.APPLICATION_JSON)
	public Response pagedlist(@PathParam("description") String description, @DefaultValue("0") @QueryParam("firstResult") Integer firstResult,
			@DefaultValue("10") @QueryParam("maxResults") Integer maxResults)
	{
		Response r = null;
//		try
//		{
//			r = ResponseUtil.success(audioVisualML.getQueryHintResult(description, new QueryHint(firstResult, maxResults)));
//		}
//		catch (Exception e)
//		{
//			System.out.println("exception in create " + e);
//			r = ResponseUtil.fatalException();
//		}
		return r;
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response create(AudioVisual audioVisual)
	{
		Response r = null;
		try
		{
			r = ResponseUtil.success(audioVisualML.save(audioVisual));
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
	public Response update(AudioVisual audioVisual)
	{
		Response r = null;
		try
		{
			r = ResponseUtil.success(audioVisualML.save(audioVisual));
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
			r = ResponseUtil.success(audioVisualML.delete(id));
		}
		catch (Exception e)
		{
			System.out.println("exception in create " + e);
			r = ResponseUtil.fatalException();
		}
		return r;
	}
}
