package com.reelbook.rest.endpoint;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import com.reelbook.core.endpoint.BaseManagerEnpoint;
import com.reelbook.core.model.support.QueryHint;
import com.reelbook.core.util.FileUtil;
import com.reelbook.model.Artist;
import com.reelbook.model.File;
import com.reelbook.rest.annotation.RequiredRole;
import com.reelbook.rest.annotation.Secured;
import com.reelbook.rest.app.RoleEnum;
import com.reelbook.rest.app.UserPrincipal;
import com.reelbook.rest.app.UserPrincipalMap;
import com.reelbook.rest.util.ResponseHeader;
import com.reelbook.rest.util.ResponseUtil;
import com.reelbook.service.manager.local.ArtistManagerLocal;
import com.reelbook.service.manager.local.FileManagerLocal;

@Stateless
@Path("/artist")
public class ArtistEndPoint extends BaseManagerEnpoint<Artist>
{
	@EJB
	private ArtistManagerLocal artistML;
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
			r = ResponseUtil.success(artistML.getQueryHintResult("", new QueryHint(0, Integer.MAX_VALUE)).getQueryList().toArray(new Artist[0]));
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
			r = ResponseUtil.success(artistML.get(id));
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
			r = ResponseUtil.success(artistML.getQueryHintResult(description, new QueryHint(firstResult, maxResults)));
		}
		catch (Exception e)
		{
			System.out.println("exception in create " + e);
			r = ResponseUtil.fatalException();
		}
		return r;
	}
	
	@GET
	@Path("/withtags")
	@Produces(MediaType.APPLICATION_JSON)
	public Response withtags(@DefaultValue("0") @QueryParam("firstResult") Integer firstResult,
			@DefaultValue("10") @QueryParam("maxResults") Integer maxResults, @QueryParam("tag") List<String> tagList)
	{
		Response r = null;
		try
		{
			r = ResponseUtil.success(artistML.getQueryHintResult(tagList, new QueryHint(firstResult, maxResults)));
		}
		catch (Exception e)
		{
			System.out.println("exception in create " + e);
			r = ResponseUtil.fatalException();
		}
		return r;
	}

	// TODO See Interface
	public Response create(Artist artist)
	{
		Response r = null;
		try
		{
			r = ResponseUtil.success(artistML.save(artist));
		}
		catch (Exception e)
		{
			System.out.println("exception in create " + e);
			r = ResponseUtil.fatalException();
		}
		return r;
	}
	
	@POST
	@Secured
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response create(Artist artist, @Context HttpServletRequest req)
	{
		Response r = null;
		try
		{
			String authorization = req.getHeader(HttpHeaders.AUTHORIZATION);
			String token = authorization.replace("Basic ", "");
			UserPrincipal authenticatedUser = UserPrincipalMap.get(token);
			Long userID = authenticatedUser.getUser().getID();
			artist.setUserID(userID);
			Response response = create(artist);
			response.getHeaders().add(ResponseHeader.REFRESH_SESSION_USER, true);
			r = response;
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
	public Response update(Artist artist)
	{
		Response r = null;
		try
		{
			r = ResponseUtil.success(artistML.save(artist));
		}
		catch (Exception e)
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
			r = ResponseUtil.success(artistML.delete(id));
		}
		catch (Exception e)
		{
			System.out.println("exception in create " + e);
			r = ResponseUtil.fatalException();
		}
		return r;
	}

	@POST
	@Path("/submit")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadFile(MultipartFormDataInput input)
	{
		String uploadName = "uploadedFile";
		Integer bufferSize = 8192;
		Response r = null;
		try
		{
			String description = input.getFormDataPart("description", String.class, null);
			Artist artist = new Artist(null, description);
			Map<String, String> bytesMap = FileUtil.getBase64Map(input, uploadName, bufferSize);
			for (Entry<String, String> entry : bytesMap.entrySet())
			{
				String fileName = entry.getKey();
				String content = entry.getValue();
				artist.setFile((new File(fileName, content)));
			}
			artistML.save(artist);
			r = ResponseUtil.success();
		}
		catch (Exception e)
		{
			System.out.println("exception in create " + e);
			r = ResponseUtil.fatalException();
		}
		return r;

	}
}
