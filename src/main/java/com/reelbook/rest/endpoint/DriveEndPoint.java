package com.reelbook.rest.endpoint;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.reelbook.core.rest.util.ResponseUtil;
import com.reelbook.core.service.util.QueryHintResult;
import com.reelbook.core.util.CompareUtil;
import com.reelbook.model.DriveCredential;
import com.reelbook.model.User;
import com.reelbook.model.dto.DriveFile;
import com.reelbook.rest.app.UserPrincipalMap;
import com.reelbook.service.manager.local.DriveManagerLocal;
import com.reelbook.service.manager.local.UserManagerLocal;

@Stateless
@Path("/drive")
public class DriveEndPoint
{
	@EJB
	private DriveManagerLocal driveML;
	@EJB
	private UserManagerLocal userML;

	@POST
	@Path("/credential/save")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response credentialSave(@FormParam("accessToken") String accessToken, @FormParam("refreshToken") String refreshToken, @Context HttpServletRequest req)
	{
		Response r = null;
		try
		{
//			Long userID = UserPrincipalMap.getUserPrincipal(req).getUser().getID();
//			User user = userML.getFULL(userID);
//			if (CompareUtil.isEmpty(user.getDriveCredential()))
//			{
//				user.setDriveCredential(new DriveCredential(user, accessToken, refreshToken));
//			}
//			else
//			{
//				user.getDriveCredential().setAccessToken(accessToken);
//				user.getDriveCredential().setAccessToken(refreshToken);
//			}
//			user = userML.save(user);
//			r = ResponseUtil.success(user.getDriveCredential());
		}
		catch (Exception e)
		{
			System.out.println("exception in create " + e);
			r = ResponseUtil.fatalException();
		}
		return r;
	}

	@GET
	@Path("/userFiles")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUserFiles(@FormParam("authCode") String authCode, @Context HttpServletRequest req)
	{
		Response r = null;
		try
		{
			Long userID = UserPrincipalMap.getUserPrincipal(req).getUser().getID();
			List<DriveFile> userVideos = driveML.getUserFiles(userID, authCode);
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