package com.reelbook.service.manager.local;

import javax.ejb.Local;
import com.reelbook.core.service.manager.local.BasePersistenceManager;
import com.reelbook.core.service.manager.local.BaseSimpleManager;
import com.reelbook.model.Artist;

@Local
public interface ArtistManagerLocal extends BasePersistenceManager<Artist>, BaseSimpleManager<Artist>
{
}