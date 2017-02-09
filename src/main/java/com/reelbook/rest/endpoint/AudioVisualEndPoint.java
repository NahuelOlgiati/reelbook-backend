package com.reelbook.rest.endpoint;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.Channels;
import java.nio.channels.WritableByteChannel;
import java.util.List;
import java.util.Map;
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
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.mp4parser.muxer.InMemRandomAccessSourceImpl;
import org.mp4parser.muxer.Movie;
import org.mp4parser.muxer.Track;
import org.mp4parser.muxer.builder.FragmentedMp4Builder;
import org.mp4parser.muxer.builder.SyncSampleIntersectFinderImpl;
import org.mp4parser.muxer.container.mp4.MovieCreator;
import org.mp4parser.tools.ByteBufferByteChannel;
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
public class AudioVisualEndPoint
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
		// r = ResponseUtil.success(audioVisualML.getQueryHintResult("", new
		// QueryHint(0, Integer.MAX_VALUE)).getQueryList().toArray(new
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
		// r =
		// ResponseUtil.success(audioVisualML.getQueryHintResult(description,
		// new QueryHint(firstResult, maxResults)));
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

			Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
			List<InputPart> inputParts = uploadForm.get(uploadName);
			for (InputPart inputPart : inputParts)
			{
				String fileName = FileUtil.getFileName(inputPart.getHeaders());
				byte[] byteArray = FileUtil.readStream(inputPart.getBody(new GenericType<InputStream>()
				{
				}), bufferSize);
				byte[] byteArrayFragmented = getFragmentedByteArray(fileName, byteArray);
				audioVisualML.addVideo(userID, new Video(fileName, byteArrayFragmented));
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

	private byte[] getFragmentedByteArray(String fileName, byte[] byteArray) throws IOException
	{
		Movie movie = MovieCreator.build(new ByteBufferByteChannel(byteArray), new InMemRandomAccessSourceImpl(byteArray), fileName);
		Movie fragmentedMovie = new Movie();
		System.out.println("Movie");
		for (Track track : movie.getTracks())
		{
			System.out.println(track.getCompositionTimeEntries());
			System.out.println("Samples().size " + track.getSamples().size());
			System.out.println("track.getSampleDurations().length " + track.getSampleDurations().length);
			for (int i = 0; i < track.getSampleDurations().length; i++)
			{
				System.out.println(i + " - " + track.getSampleDurations()[i]);

			}
			fragmentedMovie.addTrack(track);
		}
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		WritableByteChannel channel = Channels.newChannel(os);
		FragmentedMp4Builder fragmentedMp4Builder = new FragmentedMp4Builder();
		fragmentedMp4Builder.setFragmenter(new SyncSampleIntersectFinderImpl(fragmentedMovie, null, -1));
		fragmentedMp4Builder.build(fragmentedMovie).writeContainer(channel);

		System.out.println("");
		System.out.println("MovieFragmented");
		for (Track track : fragmentedMovie.getTracks())
		{
			System.out.println(track.getCompositionTimeEntries());
			System.out.println("Samples().size " + track.getSamples().size());
			System.out.println("track.getSampleDurations().length " + track.getSampleDurations().length);
			for (int i = 0; i < track.getSampleDurations().length; i++)
			{
				System.out.println(i + " - " + track.getSampleDurations()[i]);

			}
		}

		return os.toByteArray();
	}
}