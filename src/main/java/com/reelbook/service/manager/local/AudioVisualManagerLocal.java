package com.reelbook.service.manager.local;

import javax.ejb.Local;
import com.reelbook.core.exception.ManagerException;
import com.reelbook.core.service.manager.local.BasePersistenceManager;
import com.reelbook.model.AudioVisual;
import com.reelbook.model.Video;

@Local
public interface AudioVisualManagerLocal extends BasePersistenceManager<AudioVisual>
{
	public abstract Video addVideo(Long userID, Video video) throws ManagerException;
}