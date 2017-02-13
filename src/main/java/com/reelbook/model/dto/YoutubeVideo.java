package com.reelbook.model.dto;

public class YoutubeVideo
{
	private String videoID;
	private String title;
	private String thumbnailURL;

	public YoutubeVideo(String videoID, String title, String thumbnailURL)
	{
		this.videoID = videoID;
		this.title = title;
		this.thumbnailURL = thumbnailURL;
	}

	public YoutubeVideo()
	{
		this(null, null, null);
	}

	public String getVideoID()
	{
		return videoID;
	}

	public void setVideoID(String videoID)
	{
		this.videoID = videoID;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getThumbnailURL()
	{
		return thumbnailURL;
	}

	public void setThumbnailURL(String thumbnailURL)
	{
		this.thumbnailURL = thumbnailURL;
	}
}
