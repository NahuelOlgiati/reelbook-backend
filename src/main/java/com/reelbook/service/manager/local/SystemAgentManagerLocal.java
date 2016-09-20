package com.reelbook.service.manager.local;

import javax.ejb.Local;

import com.reelbook.model.SystemAgent;
import com.reelbook.model.embeddable.Document;
import com.reelbook.server.ejb.BasePersistenceManager;
import com.reelbook.server.exception.ManagerException;

@Local
public interface SystemAgentManagerLocal extends BasePersistenceManager<SystemAgent> {
	/**
	 */
	public abstract SystemAgent get(final Document d);

	/**
	 */
	public abstract SystemAgent getOrCreate(final Document d, final String firstName, final String lastName)
			throws ManagerException;
}