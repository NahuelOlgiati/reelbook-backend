package com.reelbook.service.manager.local;

import javax.ejb.Local;
import com.reelbook.core.service.manager.local.BasePersistenceManager;
import com.reelbook.model.Video;

@Local
public interface VideoManagerLocal extends BasePersistenceManager<Video>
{
}