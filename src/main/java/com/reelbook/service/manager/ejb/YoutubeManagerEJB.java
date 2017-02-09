package com.reelbook.service.manager.ejb;

import java.io.IOException;
import java.util.ArrayList;
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
import com.reelbook.core.service.manager.ejb.BaseEJB;
import com.reelbook.service.manager.local.YoutubeManagerLocal;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class YoutubeManagerEJB extends BaseEJB implements YoutubeManagerLocal
{
	public void getCaca(String refreshToken)
	{
		String clientId = "822657132611-le5ivjjco3upqr3hbqstestj6q2ip2fs.apps.googleusercontent.com";
		String clientSecret = "Q2osaQv70mZZpo5hFgL3wU1z";

		// Authorize the request.
		Credential credential = new GoogleCredential.Builder().setTransport(new NetHttpTransport()).setJsonFactory(new JacksonFactory())
				.setClientSecrets(clientId, clientSecret).build();

		credential.setRefreshToken(refreshToken);

		// This object is used to make YouTube Data API requests.
		YouTube youtube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(), credential)
				.setApplicationName("youtube-cmdline-myuploads-sample").build();

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

			}
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}