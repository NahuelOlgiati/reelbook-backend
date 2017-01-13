package com.reelbook.service.manager.ejb;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import com.reelbook.core.service.manager.ejb.BasePersistenceManagerEJB;
import com.reelbook.model.Video;
import com.reelbook.service.manager.local.VideoManagerLocal;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class VideoManagerEJB extends BasePersistenceManagerEJB<Video> implements VideoManagerLocal
{
	@Override
	public Class<Video> getModelClass()
	{
		return Video.class;
	}
}