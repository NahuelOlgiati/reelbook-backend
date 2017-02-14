package com.reelbook.service.manager.ejb;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.reelbook.core.service.manager.ejb.BaseEJB;
import com.reelbook.model.User;
import com.reelbook.model.dto.DriveFile;
import com.reelbook.rest.app.ParamApp;
import com.reelbook.service.manager.local.DriveManagerLocal;
import com.reelbook.service.manager.local.UserManagerLocal;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class DriveManagerEJB extends BaseEJB implements DriveManagerLocal
{
	@EJB
	private UserManagerLocal userML;

	final static long NUMBER_OF_VIDEOS_RETURNED = 25;
	private static final String FILE_LIST_REQUIRED_FIELDS = "nextPageToken, files(id, name, mimeType)";

	@Override
	public List<DriveFile> getUserFiles(Long userID, String authCode)
	{
		List<DriveFile> userVideos = new ArrayList<>();
		// Call the API's channels.list method to retrieve the
		// resource that represents the authenticated user's channel.
		// In the API response, only include channel information needed for
		// this use case. The channel's contentDetails part contains
		// playlist IDs relevant to the channel, including the ID for the
		// list that contains videos uploaded to the channel.
		try
		{
			Drive drive = getDrive(userID, authCode);
			Drive.Files.List search = drive.files().list().setQ("trashed=false")
					.setFields(FILE_LIST_REQUIRED_FIELDS);

			// search.setForMine(Boolean.TRUE);

			// Set your developer key from the {{ Google Cloud Console }} for
			// non-authenticated requests. See:
			// {{ https://cloud.google.com/console }}
//			search.setKey(ParamApp.getGoogleApiKey());
			search.setKey("822657132611");
			// search.setQ(queryTerm);

			// Restrict the search results to only include videos. See:
			// https://developers.google.com/drive/v3/docs/search/list#type
			// search.setType("video");

			// To increase efficiency, only retrieve the fields that the
			// application uses.
			// search.setMaxResults(NUMBER_OF_VIDEOS_RETURNED);

			// Call the API and print results.
			FileList searchResponse = search.execute();
			List<File> searchResultList = searchResponse.getFiles();
			if (searchResultList != null)
			{
				Iterator<File> iterator = searchResultList.iterator();
				while (iterator.hasNext())
				{
					File driveFile = iterator.next();

					// Confirm that the result represents a video. Otherwise, the
					// item will not contain a video ID.
					// if (rId.getKind().equals("drive#video"))
					// {
					// Thumbnail thumbnail = singleVideo.getSnippet().getThumbnails().getDefault();
					userVideos.add(new DriveFile(driveFile.getId(), driveFile.getName(), driveFile.getMimeType()));
					// }
				}
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return userVideos;
	}

	private Drive getDrive(Long userID, String authCode)
	{
		User user = userML.getFULL(userID);

		// Authorize the request.
		Credential credential = new GoogleCredential.Builder().setTransport(new NetHttpTransport()).setJsonFactory(new JacksonFactory())
				.setClientSecrets(ParamApp.getGoogleClientID(), ParamApp.getGoogleClientSecret()).build();
		credential.setRefreshToken(user.getOauthCredential().getDriveRefreshToken());

		// We've got auth code from Google and should request an access token and a refresh token.
		/*
		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(new NetHttpTransport(), new JacksonFactory(), ParamApp.getGoogleClientID(), ParamApp.getGoogleClientSecret(),
				Arrays.asList(DriveScopes.DRIVE_READONLY)).setAccessType("offline").setApprovalPrompt("force").build();

		GoogleTokenResponse response = null;
		try
		{
			response = flow.newTokenRequest(authCode).setRedirectUri("urn:ietf:wg:oauth:2.0:oob").execute();
			System.out.println("AccesToken: " + response.getAccessToken());
			System.out.println("RefreshToken: " + response.getRefreshToken());
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/

		// This object is used to make Drive Data API requests.
		return new Drive.Builder(new NetHttpTransport(), new JacksonFactory(), credential).setApplicationName("drive-reelbook").build();
	}
}