package com.reelbook.service.manager.local;

import javax.ejb.Local;

import com.reelbook.core.service.manager.local.BasePersistenceManager;
import com.reelbook.model.AudioVisual;

@Local
public interface AudioVisualManagerLocal extends BasePersistenceManager<AudioVisual>
{
}