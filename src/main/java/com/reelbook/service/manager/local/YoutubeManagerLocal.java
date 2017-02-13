package com.reelbook.service.manager.local;

import java.util.List;
import javax.ejb.Local;
import com.reelbook.model.dto.YoutubeVideo;

@Local
public interface YoutubeManagerLocal
{
	public abstract List<YoutubeVideo> getUserVideos(Long userID);
}