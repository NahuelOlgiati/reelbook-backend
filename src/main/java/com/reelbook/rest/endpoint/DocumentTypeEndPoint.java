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
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import com.reelbook.core.exception.ManagerException;
import com.reelbook.core.model.support.QueryHint;
import com.reelbook.core.service.manager.ejb.BaseEJB;
import com.reelbook.model.DocumentType;
import com.reelbook.rest.annotation.RequiredRole;
import com.reelbook.rest.app.RoleEnum;
import com.reelbook.rest.util.ResponseUtil;
import com.reelbook.service.manager.local.DocumentTypeManagerLocal;

// @Secured
@Stateless
@Path("/documentType")
public class DocumentTypeEndPoint extends BaseEJB
{
	@EJB
	private DocumentTypeManagerLocal documentTypeML;

	@GET
	@RequiredRole({RoleEnum.ADMIN})
	@Produces(MediaType.APPLICATION_JSON)
	public DocumentType[] get()
	{
		return documentTypeML.getQueryHintResult("", new QueryHint(0, Integer.MAX_VALUE)).getQueryList().toArray(new DocumentType[0]);
	}

	@GET
	@Path("/get:{id:[0-9][0-9]*}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response findById(@PathParam("id") Long id)
	{
		Response r = null;
		try
		{
			r = ResponseUtil.success(documentTypeML.get(id));
		}
		catch (Exception e)
		{
			System.out.println("exception in create " + e);
			r = ResponseUtil.fatalException();
		}
		return r;
	}

	@GET
	@Path("/autocomplete:{description}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response autocomplete(@PathParam("description") String description, @DefaultValue("0") @QueryParam("firstResult") Integer firstResult,
			@DefaultValue("10") @QueryParam("maxResults") Integer maxResults)
	{
		Response r = null;
		try
		{
			r = ResponseUtil.success(documentTypeML.getQueryHintResult(description, new QueryHint(firstResult, maxResults)));
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
	public Response create(DocumentType documentType)
	{
		Response r = null;
		try
		{
			r = ResponseUtil.success(documentTypeML.save(documentType));
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
	public Response update(DocumentType documentType)
	{
		Response r = null;
		try
		{
			r = ResponseUtil.success(documentTypeML.save(documentType));
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
	public Response deleteById(@PathParam("id") Long id)
	{
		Response r = null;
		try
		{
			r = ResponseUtil.success(documentTypeML.delete(id));
		}
		catch (Exception e)
		{
			System.out.println("exception in create " + e);
			r = ResponseUtil.fatalException();
		}
		return r;
	}

	@POST
	@Path("/upload")
	@Consumes("multipart/form-data")
	public Response uploadFile(MultipartFormDataInput input)
	{
//		String uploadName = "uploadedFile";
//		String uploadFilePath = "/home/tallion.com.ar/nolgiati/Desktop/upload/";
//		Integer bufferSize = 8192;
//		try
//		{
//			FileUtil.upload(input, uploadName, uploadFilePath, bufferSize);
//		}
//		catch (IOException e)
//		{
//			e.printStackTrace();
//		}
		return ResponseUtil.success();

	}
}
