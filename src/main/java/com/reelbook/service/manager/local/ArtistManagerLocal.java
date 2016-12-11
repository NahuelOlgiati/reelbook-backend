package com.reelbook.service.manager.local;

import java.util.List;

import javax.ejb.Local;

import com.reelbook.core.model.support.QueryHint;
import com.reelbook.core.service.manager.local.BasePersistenceManager;
import com.reelbook.core.service.manager.local.BaseSimpleManager;
import com.reelbook.core.service.util.QueryHintResult;
import com.reelbook.model.Artist;

@Local
public interface ArtistManagerLocal extends BasePersistenceManager<Artist>, BaseSimpleManager<Artist>
{
	public abstract QueryHintResult<Artist> getQueryHintResult(List<String> description, QueryHint queryHint);
}