package com.reelbook.rest.endpoint;

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
import com.reelbook.model.User;
import com.reelbook.rest.app.UserPrincipalMap;
import com.reelbook.service.manager.local.OauthManagerLocal;
import com.reelbook.service.manager.local.UserManagerLocal;

@Stateless
@Path("/oauth")
public class OauthEndPoint
{
	@EJB
	private OauthManagerLocal oauthML;
	@EJB
	private UserManagerLocal userML;

	@GET
	@Path("/credential/youtube/has")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response hasYoutubeCredential(@Context HttpServletRequest req)
	{
		Response r = null;
		try
		{
			Long userID = UserPrincipalMap.getUserPrincipal(req).getUser().getID();
			User user = userML.getFULL(userID);
			Boolean hasCredential = user.getOauthCredential() == null ? Boolean.FALSE : user.getOauthCredential().hasYoutubeCredential();
			r = ResponseUtil.success(hasCredential);
		}
		catch (Exception e)
		{
			System.out.println("exception in hasYoutubeCredential " + e);
			r = ResponseUtil.fatalException();
		}
		return r;
	}

	@POST
	@Path("/credential/youtube/save")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response saveYoutubeCredential(@FormParam("authCode") String authCode, @FormParam("redirectUri") String redirectUri, @Context HttpServletRequest req)
	{
		Response r = null;
		try
		{
			Long userID = UserPrincipalMap.getUserPrincipal(req).getUser().getID();
			oauthML.saveYoutubeCredential(userID, authCode, redirectUri);
			User user = userML.getFULL(userID);
			Boolean hasCredential = user.getOauthCredential() == null ? Boolean.FALSE : user.getOauthCredential().hasYoutubeCredential();
			r = ResponseUtil.success(hasCredential);
		}
		catch (Exception e)
		{
			System.out.println("exception in create " + e);
			r = ResponseUtil.fatalException();
		}
		return r;
	}

	@GET
	@Path("/credential/drive/has")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response hasDriveCredential(@Context HttpServletRequest req)
	{
		Response r = null;
		try
		{
			Long userID = UserPrincipalMap.getUserPrincipal(req).getUser().getID();
			User user = userML.getFULL(userID);
			Boolean hasCredential = user.getOauthCredential() == null ? Boolean.FALSE : user.getOauthCredential().hasDriveCredential();
			r = ResponseUtil.success(hasCredential);
		}
		catch (Exception e)
		{
			System.out.println("exception in hasYoutubeCredential " + e);
			r = ResponseUtil.fatalException();
		}
		return r;
	}

	@POST
	@Path("/credential/drive/save")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response saveDriveCredential(@FormParam("authCode") String authCode, @FormParam("redirectUri") String redirectUri, @Context HttpServletRequest req)
	{
		Response r = null;
		try
		{
			Long userID = UserPrincipalMap.getUserPrincipal(req).getUser().getID();
			oauthML.saveDriveCredential(userID, authCode, redirectUri);
			User user = userML.getFULL(userID);
			Boolean hasCredential = user.getOauthCredential() == null ? Boolean.FALSE : user.getOauthCredential().hasDriveCredential();
			r = ResponseUtil.success(hasCredential);
		}
		catch (Exception e)
		{
			System.out.println("exception in create " + e);
			r = ResponseUtil.fatalException();
		}
		return r;
	}
}