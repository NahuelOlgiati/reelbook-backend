package com.reelbook.service.manager.ejb;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import com.reelbook.core.service.manager.ejb.BasePersistenceManagerEJB;
import com.reelbook.model.File;
import com.reelbook.service.manager.local.FileManagerLocal;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class FileManagerEJB extends BasePersistenceManagerEJB<File> implements FileManagerLocal
{
	/**
	 */
	@Override
	public Class<File> getModelClass()
	{
		return File.class;
	}
}