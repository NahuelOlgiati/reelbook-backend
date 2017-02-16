package com.reelbook.rest.endpoint;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.reelbook.core.rest.util.ResponseUtil;
import com.reelbook.core.service.util.QueryHintResult;
import com.reelbook.model.dto.DriveFile;
import com.reelbook.rest.app.UserPrincipalMap;
import com.reelbook.service.manager.local.DriveManagerLocal;

@Stateless
@Path("/drive")
public class DriveEndPoint
{
	@EJB
	private DriveManagerLocal driveML;

	@GET
	@Path("/userFiles")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUserFiles(@Context HttpServletRequest req)
	{
		Response r = null;
		try
		{
			Long userID = UserPrincipalMap.getUserPrincipal(req).getUser().getID();
			List<DriveFile> userVideos = driveML.getUserFiles(userID);
			r = ResponseUtil.success(new QueryHintResult<DriveFile>(25, userVideos));
		}
		catch (Exception e)
		{
			System.out.println("exception in create " + e);
			r = ResponseUtil.fatalException();
		}
		return r;
	}
}