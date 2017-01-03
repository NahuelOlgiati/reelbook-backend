package com.reelbook.rest.endpoint;

import java.util.Map;
import java.util.Map.Entry;
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
import com.reelbook.core.endpoint.BaseManagerEnpoint;
import com.reelbook.core.exception.ManagerException;
import com.reelbook.core.util.FileUtil;
import com.reelbook.model.AudioVisual;
import com.reelbook.model.Video;
import com.reelbook.rest.app.UserPrincipal;
import com.reelbook.rest.app.UserPrincipalMap;
import com.reelbook.rest.util.ResponseUtil;
import com.reelbook.service.manager.local.AudioVisualManagerLocal;

@Stateless
@Path("/audiovisual")
public class AudioVisualEndPoint extends BaseManagerEnpoint<AudioVisual>
{
	@EJB
	private AudioVisualManagerLocal audioVisualML;

	// @GET
	// @RequiredRole({RoleEnum.ADMIN})
	// @Produces(MediaType.APPLICATION_JSON)
	public Response getList()
	{
		Response r = null;
		// try
		// {
		// r = ResponseUtil.success(audioVisualML.getQueryHintResult("", new QueryHint(0, Integer.MAX_VALUE)).getQueryList().toArray(new
		// AudioVisual[0]));
		// }
		// catch (Exception e)
		// {
		// System.out.println("exception in create " + e);
		// r = ResponseUtil.fatalException();
		// }
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

	// @Override
	// @GET
	// @Path("/pagedlist:{description}")
	// @Produces(MediaType.APPLICATION_JSON)
	public Response pagedlist(@PathParam("description") String description, @DefaultValue("0") @QueryParam("firstResult") Integer firstResult,
			@DefaultValue("10") @QueryParam("maxResults") Integer maxResults)
	{
		Response r = null;
		// try
		// {
		// r = ResponseUtil.success(audioVisualML.getQueryHintResult(description, new QueryHint(firstResult, maxResults)));
		// }
		// catch (Exception e)
		// {
		// System.out.println("exception in create " + e);
		// r = ResponseUtil.fatalException();
		// }
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
			String token = input.getFormDataPart("token", String.class, null);
			UserPrincipal authenticatedUser = UserPrincipalMap.get(token);
			Long userID = authenticatedUser.getUser().getID();

			Map<String, byte[]> bytesMap = FileUtil.getByteArrayMap(input, uploadName, bufferSize);
			for (Entry<String, byte[]> entry : bytesMap.entrySet())
			{
				String fileName = entry.getKey();
				byte[] content = entry.getValue();
				audioVisualML.addVideo(userID, new Video(fileName, content));
			}
			r = ResponseUtil.success();
		}
		catch (Exception e)
		{
			System.out.println("exception in create " + e);
			r = ResponseUtil.fatalException();
		}
		return r;

	}

	// @GET
	// @Produces(MediaType.TEXT_PLAIN)
	// @Path("/{nodeId}/{depth}")
	// public Response hello(@PathParam("nodeId") long nodeId, @PathParam("depth") int depth) {
	// Node node = database.getNodeById(nodeId);
	//
	// final Traverser paths = Traversal.description()
	// .depthFirst()
	// .relationships(DynamicRelationshipType.withName("whatever"))
	// .evaluator( Evaluators.toDepth(depth) )
	// .traverse(node);
	//
	// StreamingOutput stream = new StreamingOutput() {
	// @Override
	// public void write(OutputStream os) throws IOException, WebApplicationException {
	// Writer writer = new BufferedWriter(new OutputStreamWriter(os));
	//
	// for (org.neo4j.graphdb.Path path : paths) {
	// writer.write(path.toString() + "\n");
	// }
	// writer.flush();
	// }
	// };
	//
	// return Response.ok(stream).build();
	// }
}
