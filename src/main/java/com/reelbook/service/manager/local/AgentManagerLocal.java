package com.reelbook.service.manager.local;

import com.reelbook.core.service.manager.local.BasePersistenceManager;
import com.reelbook.model.embeddable.Document;
import com.reelbook.model.msc.Agent;

public interface AgentManagerLocal<T extends Agent> extends BasePersistenceManager<T>
{

	public abstract T get(final Document document);
}