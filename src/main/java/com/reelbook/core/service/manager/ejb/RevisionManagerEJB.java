package com.reelbook.core.service.manager.ejb;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import com.reelbook.core.model.Revision;
import com.reelbook.core.service.manager.local.BaseRevisionManager;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class RevisionManagerEJB implements BaseRevisionManager<Revision>
{
	/**
	 */
	@Override
	public Class<Revision> getRevisionModelClass()
	{
		return Revision.class;
	}
}