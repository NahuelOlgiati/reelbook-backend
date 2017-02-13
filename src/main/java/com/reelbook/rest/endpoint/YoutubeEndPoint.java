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
import com.reelbook.core.util.CompareUtil;
import com.reelbook.model.User;
import com.reelbook.model.YoutubeCredential;
import com.reelbook.rest.app.UserPrincipalMap;
import com.reelbook.service.manager.local.UserManagerLocal;
import com.reelbook.service.manager.local.YoutubeManagerLocal;

@Stateless
@Path("/youtube")
public class YoutubeEndPoint
{
	@EJB
	private YoutubeManagerLocal youtubeML;
	@EJB
	private UserManagerLocal userML;

	@POST
	@Path("/credential/save")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response credentialSave(@FormParam("accessToken") String accessToken, @FormParam("refreshToken") String refreshToken,
			@Context HttpServletRequest req)
	{
		Response r = null;
		try
		{
			Long userID = UserPrincipalMap.getUserId(req);
			User user = userML.getFULL(userID);
			if (CompareUtil.isEmpty(user.getYoutubeCredential()))
			{
				user.setYoutubeCredential(new YoutubeCredential(user, accessToken, refreshToken));
			}
			else
			{
				user.getYoutubeCredential().setAccessToken(accessToken);
				user.getYoutubeCredential().setAccessToken(refreshToken);
			}
			user = userML.save(user);
			r = ResponseUtil.success(user.getYoutubeCredential());
		}
		catch (Exception e)
		{
			System.out.println("exception in create " + e);
			r = ResponseUtil.fatalException();
		}
		return r;
	}

	@GET
	@Path("/channels")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getChannels(@Context HttpServletRequest req)
	{
		Response r = null;
		try
		{
			Long userID = UserPrincipalMap.getUserId(req);
			User user = userML.getFULL(userID);
			user = userML.save(user);
			youtubeML.getChannel(user.getYoutubeCredential().getRefreshToken());
			System.out.println("");
			System.out.println("SPACE");
			System.out.println("");
			youtubeML.getSearch(user.getYoutubeCredential().getRefreshToken());
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