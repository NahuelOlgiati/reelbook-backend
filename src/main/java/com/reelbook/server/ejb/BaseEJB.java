package com.reelbook.server.ejb;

import java.security.Principal;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.reelbook.core.exception.BaseException;

public abstract class BaseEJB {
	
	@PersistenceContext
	protected EntityManager em;

	@Resource
	protected SessionContext sc;

//	@EJB
//	protected BaseRevisionManager<? extends BaseRevisionModel> rm;

	/**
	 */
	public Principal getPrincipal() {
		return sc.getCallerPrincipal();
	}

	/**
	 */
	protected void doAudit() throws BaseException {
		// AuditReaderFactory.get(em).getCurrentRevision(rm.getRevisionModelClass(),
		// false).setUserName(getPrincipal().getName());
	}
}