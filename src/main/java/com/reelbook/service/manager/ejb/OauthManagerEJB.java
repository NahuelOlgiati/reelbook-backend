package com.reelbook.service.manager.ejb;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import com.google.gson.Gson;
import com.reelbook.core.service.manager.ejb.BaseEJB;
import com.reelbook.core.util.CompareUtil;
import com.reelbook.model.OauthCredential;
import com.reelbook.model.User;
import com.reelbook.model.dto.OauthTokenResponse;
import com.reelbook.rest.app.ParamApp;
import com.reelbook.service.manager.local.OauthManagerLocal;
import com.reelbook.service.manager.local.UserManagerLocal;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class OauthManagerEJB extends BaseEJB implements OauthManagerLocal
{
	@EJB
	private UserManagerLocal userML;

	@Override
	public void saveYoutubeCredential(Long userID, String authCode, String redirectUri)
	{
		try
		{
			User user = userML.getFULL(userID);
			OauthTokenResponse oauthToken = getOauthToken(authCode, redirectUri);
			if (CompareUtil.isEmpty(user.getOauthCredential()))
			{
				user.setOauthCredential(new OauthCredential(user));

			}
			user.getOauthCredential().setYoutubeAccessToken(oauthToken.getAccess_token());
			user.getOauthCredential().setYoutubeRefreshToken(oauthToken.getRefresh_token());
			userML.save(user);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void saveDriveCredential(Long userID, String code, String redirectUri)
	{
		try
		{
			User user = userML.getFULL(userID);
			OauthTokenResponse oauthToken = getOauthToken(code, redirectUri);
			if (CompareUtil.isEmpty(user.getOauthCredential()))
			{
				user.setOauthCredential(new OauthCredential(user));

			}
			user.getOauthCredential().setDriveAccessToken(oauthToken.getAccess_token());
			user.getOauthCredential().setDriveRefreshToken(oauthToken.getRefresh_token());
			userML.save(user);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private OauthTokenResponse getOauthToken(String authCode, String redirectUri) throws Exception
	{
		String request = "https://accounts.google.com/o/oauth2/token";
		String urlParameters = "code=" + authCode + "&redirect_uri=" + redirectUri + "&client_id=" + ParamApp.getGoogleClientID() + "&client_secret=" + ParamApp.getGoogleClientSecret()
				+ "&grant_type=authorization_code";
		byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
		int postDataLength = postData.length;
		URL url = new URL(request);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setInstanceFollowRedirects(false);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		conn.setRequestProperty("charset", "utf-8");
		conn.setRequestProperty("Content-Length", Integer.toString(postDataLength));
		conn.setUseCaches(false);
		try (DataOutputStream wr = new DataOutputStream(conn.getOutputStream()))
		{
			wr.write(postData);
			wr.flush();
			wr.close();
		}

		final Gson gson = new Gson();
		final BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		return gson.fromJson(reader, OauthTokenResponse.class);
	}
}