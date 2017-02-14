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
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.ResourceId;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Thumbnail;
import com.reelbook.core.service.manager.ejb.BaseEJB;
import com.reelbook.model.User;
import com.reelbook.model.dto.YoutubeVideo;
import com.reelbook.rest.app.ParamApp;
import com.reelbook.service.manager.local.UserManagerLocal;
import com.reelbook.service.manager.local.YoutubeManagerLocal;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class YoutubeManagerEJB extends BaseEJB implements YoutubeManagerLocal
{
	@EJB
	private UserManagerLocal userML;

	final static long NUMBER_OF_VIDEOS_RETURNED = 25;

	@Override
	public List<YoutubeVideo> getUserVideos(Long userID)
	{
		List<YoutubeVideo> userVideos = new ArrayList<>();
		// Call the API's channels.list method to retrieve the
		// resource that represents the authenticated user's channel.
		// In the API response, only include channel information needed for
		// this use case. The channel's contentDetails part contains
		// playlist IDs relevant to the channel, including the ID for the
		// list that contains videos uploaded to the channel.
		try
		{
			YouTube youtube = getYoutube(userID);
			YouTube.Search.List search = youtube.search().list("id,snippet");
			search.setForMine(Boolean.TRUE);

			// Set your developer key from the {{ Google Cloud Console }} for
			// non-authenticated requests. See:
			// {{ https://cloud.google.com/console }}
			search.setKey(ParamApp.getGoogleApiKey());
			// search.setQ(queryTerm);

			// Restrict the search results to only include videos. See:
			// https://developers.google.com/youtube/v3/docs/search/list#type
			search.setType("video");

			// To increase efficiency, only retrieve the fields that the
			// application uses.
			search.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)");
			search.setMaxResults(NUMBER_OF_VIDEOS_RETURNED);

			// Call the API and print results.
			SearchListResponse searchResponse = search.execute();
			List<SearchResult> searchResultList = searchResponse.getItems();
			if (searchResultList != null)
			{
				Iterator<SearchResult> iterator = searchResultList.iterator();
				while (iterator.hasNext())
				{
					SearchResult singleVideo = iterator.next();
					ResourceId rId = singleVideo.getId();

					// Confirm that the result represents a video. Otherwise, the
					// item will not contain a video ID.
					if (rId.getKind().equals("youtube#video"))
					{
						Thumbnail thumbnail = singleVideo.getSnippet().getThumbnails().getDefault();
						userVideos.add(new YoutubeVideo(rId.getVideoId(), singleVideo.getSnippet().getTitle(), thumbnail.getUrl()));
					}
				}
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return userVideos;
	}

	private YouTube getYoutube(Long userID)
	{
		User user = userML.getFULL(userID);

		// Authorize the request.
		Credential credential = new GoogleCredential.Builder().setTransport(new NetHttpTransport()).setJsonFactory(new JacksonFactory())
				.setClientSecrets(ParamApp.getGoogleClientID(), ParamApp.getGoogleClientSecret()).build();
		credential.setRefreshToken(user.getOauthCredential().getYoutubeRefreshToken());

		// This object is used to make YouTube Data API requests.
		return new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(), credential).setApplicationName("youtube-reelbook").build();
	}
}