package com.reelbook.service.manager.ejb;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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
import com.google.api.services.youtube.model.Channel;
import com.google.api.services.youtube.model.ChannelListResponse;
import com.google.api.services.youtube.model.PlaylistItem;
import com.google.api.services.youtube.model.PlaylistItemListResponse;
import com.google.api.services.youtube.model.ResourceId;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Thumbnail;
import com.reelbook.core.service.manager.ejb.BaseEJB;
import com.reelbook.service.manager.local.YoutubeManagerLocal;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class YoutubeManagerEJB extends BaseEJB implements YoutubeManagerLocal
{
	@Override
	public void getChannel(String refreshToken)
	{
		YouTube youtube = getYoutube(refreshToken);

		// Call the API's channels.list method to retrieve the
		// resource that represents the authenticated user's channel.
		// In the API response, only include channel information needed for
		// this use case. The channel's contentDetails part contains
		// playlist IDs relevant to the channel, including the ID for the
		// list that contains videos uploaded to the channel.
		YouTube.Channels.List channelRequest;
		try
		{
			channelRequest = youtube.channels().list("contentDetails");
			channelRequest.setMine(true);
			channelRequest.setFields("items/contentDetails,nextPageToken,pageInfo");
			ChannelListResponse channelResult = channelRequest.execute();

			List<Channel> channelsList = channelResult.getItems();

			if (channelsList != null)
			{
				// The user's default channel is the first item in the list.
				// Extract the playlist ID for the channel's videos from the
				// API response.
				String uploadPlaylistId = channelsList.get(0).getContentDetails().getRelatedPlaylists().getUploads();

				// Define a list to store items in the list of uploaded videos.
				List<PlaylistItem> playlistItemList = new ArrayList<PlaylistItem>();

				// Retrieve the playlist of the channel's uploaded videos.
				YouTube.PlaylistItems.List playlistItemRequest = youtube.playlistItems().list("id,contentDetails,snippet");
				playlistItemRequest.setPlaylistId(uploadPlaylistId);

				// Only retrieve data used in this application, thereby making
				// the application more efficient. See:
				// https://developers.google.com/youtube/v3/getting-started#partial
				playlistItemRequest.setFields("items(contentDetails/videoId,snippet/title,snippet/publishedAt),nextPageToken,pageInfo");

				String nextToken = "";

				// Call the API one or more times to retrieve all items in the
				// list. As long as the API response returns a nextPageToken,
				// there are still more items to retrieve.
				do
				{
					playlistItemRequest.setPageToken(nextToken);
					PlaylistItemListResponse playlistItemResult = playlistItemRequest.execute();

					playlistItemList.addAll(playlistItemResult.getItems());

					nextToken = playlistItemResult.getNextPageToken();
				}
				while (nextToken != null);

				// Prints information about the results.
				prettyPrint(playlistItemList.size(), playlistItemList.iterator());
			}
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	final static long NUMBER_OF_VIDEOS_RETURNED = 25;

	@Override
	public void getSearch(String refreshToken)
	{
		YouTube youtube = getYoutube(refreshToken);

		// Call the API's channels.list method to retrieve the
		// resource that represents the authenticated user's channel.
		// In the API response, only include channel information needed for
		// this use case. The channel's contentDetails part contains
		// playlist IDs relevant to the channel, including the ID for the
		// list that contains videos uploaded to the channel.
		try
		{
			YouTube.Search.List search = youtube.search().list("id,snippet");

			// Set your developer key from the {{ Google Cloud Console }} for
			// non-authenticated requests. See:
			// {{ https://cloud.google.com/console }}
			String apiKey = "reelbook-157618";
			search.setKey(apiKey);
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
				prettyPrint(searchResultList.iterator(), "");
			}
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private YouTube getYoutube(String refreshToken)
	{
		String clientId = "822657132611-le5ivjjco3upqr3hbqstestj6q2ip2fs.apps.googleusercontent.com";
		String clientSecret = "Q2osaQv70mZZpo5hFgL3wU1z";

		// Authorize the request.
		Credential credential = new GoogleCredential.Builder().setTransport(new NetHttpTransport()).setJsonFactory(new JacksonFactory())
				.setClientSecrets(clientId, clientSecret).build();

		credential.setRefreshToken(refreshToken);

		// This object is used to make YouTube Data API requests.
		return new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(), credential).setApplicationName("youtube-cmdline-myuploads-sample")
				.build();
	}

	private static void prettyPrint(int size, Iterator<PlaylistItem> playlistEntries)
	{
		System.out.println("=============================================================");
		System.out.println("\t\tTotal Videos Uploaded: " + size);
		System.out.println("=============================================================\n");

		while (playlistEntries.hasNext())
		{
			PlaylistItem playlistItem = playlistEntries.next();
			System.out.println(" video name  = " + playlistItem.getSnippet().getTitle());
			System.out.println(" video id    = " + playlistItem.getContentDetails().getVideoId());
			System.out.println(" upload date = " + playlistItem.getSnippet().getPublishedAt());
			System.out.println("\n-------------------------------------------------------------\n");
		}
	}

	private static void prettyPrint(Iterator<SearchResult> iteratorSearchResults, String query)
	{

		System.out.println("\n=============================================================");
		System.out.println("   First " + NUMBER_OF_VIDEOS_RETURNED + " videos for search on \"" + query + "\".");
		System.out.println("=============================================================\n");

		if (!iteratorSearchResults.hasNext())
		{
			System.out.println(" There aren't any results for your query.");
		}

		while (iteratorSearchResults.hasNext())
		{

			SearchResult singleVideo = iteratorSearchResults.next();
			ResourceId rId = singleVideo.getId();

			// Confirm that the result represents a video. Otherwise, the
			// item will not contain a video ID.
			if (rId.getKind().equals("youtube#video"))
			{
				Thumbnail thumbnail = singleVideo.getSnippet().getThumbnails().getDefault();

				System.out.println(" Video Id" + rId.getVideoId());
				System.out.println(" Title: " + singleVideo.getSnippet().getTitle());
				System.out.println(" Thumbnail: " + thumbnail.getUrl());
				System.out.println("\n-------------------------------------------------------------\n");
			}
		}
	}
}