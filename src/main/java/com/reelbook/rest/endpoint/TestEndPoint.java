package com.reelbook.rest.endpoint;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import com.reelbook.core.exception.ValidationException;
import com.reelbook.core.rest.util.ResponseUtil;
import com.reelbook.model.Profile;
import com.reelbook.model.User;
import com.reelbook.service.msg.DBSMsgHandler;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Path("/test")
@Api(value = "test")
public class TestEndPoint {

//	@GET
//	@Path("/text")
//	@ApiOperation(value = "Test Text Plain", response = String.class)
//	@Produces(MediaType.TEXT_PLAIN)
//	public String text() {
//		return "Howdy at " + new Date();
//	}
	
	@GET
	@Path("/ip")
	@ApiOperation(value = "Get Req IP", response = Response.class)
	@Produces(MediaType.TEXT_PLAIN)
	public Response ip(@Context HttpServletRequest req) {
		String remoteHost = req.getRemoteHost();
	    String remoteAddr = req.getRemoteAddr();
	    int remotePort = req.getRemotePort();
	    String msg = remoteHost + " (" + remoteAddr + ":" + remotePort + ")";
	    return Response.ok(msg).build();
	}

	@GET
	@Path("/model")
	@ApiOperation(value = "Test Text Plain", response = Response.class)
	@Produces(MediaType.APPLICATION_JSON)
	public Response model() {
		List<Profile> profiles = new ArrayList<>();
		profiles.add(new Profile("Perfil", null));
		User newUser = new User("Hola", "caracola", "caracola", "caracola", profiles);
		return Response.ok(newUser).build();
	}

//	@GET
//	@Path("/auth")
//	@ApiOperation(value = "Finds Pets by status", response = String.class)
//	@Produces(MediaType.TEXT_PLAIN)
//	public String auth() {
//		return "Howdy at " + new Date();
//	}

//	@GET
//	@Path("/dbmsg")
//	@ApiOperation(value = "Test db msg", response = String.class)
//	@Produces(MediaType.TEXT_PLAIN)
//	public String dbmsg() {
//		return DBSMsgHandler.getMsg("test");
//	}

	@GET
	@Path("/reloaddbmsg")
	@ApiOperation(value = "Reload db msg", response = Response.class)
	@Produces(MediaType.TEXT_PLAIN)
	public Response cleardbmsg() {
		DBSMsgHandler.reload();
		return ResponseUtil.success();
	}

	@GET
	@Path("/validation")
	@ApiOperation(value = "Validation msg", response = Response.class)
	@Produces(MediaType.TEXT_PLAIN)
	public Response validation() {
		try {
			List<String> messages = new ArrayList<String>();
			messages.add("PUM1");
			messages.add("PUM2");
			messages.add("PUM3");
			ValidationException v = new ValidationException(messages);
			return ResponseUtil.exceptionMessage(v.getMessages());
		} catch (Exception e) {
			return ResponseUtil.fatalException();
		}
	}
	
	final int chunk_size = 1024 * 1024; // 1MB chunks
	
	@GET
	@Path("/mp4")
    @Produces("video/mp4")
    public Response streamVideo(@HeaderParam("Range") String range) throws Exception {
        File file = new File("/home/nahuel/Desktop/reelbook/reelbook-backend/src/main/resources/test.mp4");
        return buildStream(file, range);
    }

    /**
     * response = target.header("Range", "bytes=0-50").get(ClientResponse.class);
     * @param asset Media file
     * @param range range header
     * @return Streaming output
     * @throws Exception IOException if an error occurs in streaming.
     */
    private Response buildStream(final File asset, final String range) throws Exception {
        // range not requested : Firefox, Opera, IE do not send range headers
        if (range == null) {
            StreamingOutput streamer = new StreamingOutput() {
                @Override
                public void write(final OutputStream output) throws IOException, WebApplicationException {

                    final FileChannel inputChannel = new FileInputStream(asset).getChannel();
                    final WritableByteChannel outputChannel = Channels.newChannel(output);
                    try {
                        inputChannel.transferTo(0, inputChannel.size(), outputChannel);
                    } finally {
                        // closing the channels
                        inputChannel.close();
                        outputChannel.close();
                    }
                }
            };
            return Response.ok(streamer).status(200).header(HttpHeaders.CONTENT_LENGTH, asset.length()).build();
        }

        String[] ranges = range.split("=")[1].split("-");
        final int from = Integer.parseInt(ranges[0]);
        /**
         * Chunk media if the range upper bound is unspecified. Chrome sends "bytes=0-"
         */
        int to = chunk_size + from;
        if (to >= asset.length()) {
            to = (int) (asset.length() - 1);
        }
        if (ranges.length == 2) {
            to = Integer.parseInt(ranges[1]);
        }

        final String responseRange = String.format("bytes %d-%d/%d", from, to, asset.length());
        final RandomAccessFile raf = new RandomAccessFile(asset, "r");
        raf.seek(from);

        final int len = to - from + 1;
        final MediaStreamer streamer = new MediaStreamer(len, raf);
        Response.ResponseBuilder res = Response.ok(streamer).status(206)
                .header("Accept-Ranges", "bytes")
                .header("Content-Range", responseRange)
                .header(HttpHeaders.CONTENT_LENGTH, streamer.getLenth())
                .header(HttpHeaders.LAST_MODIFIED, new Date(asset.lastModified()));
        return res.build();
    }
}
