package com.reelbook.service.manager.ejb;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import com.reelbook.core.service.manager.ejb.BasePersistenceManagerEJB;
import com.reelbook.model.AudioVisual;
import com.reelbook.service.manager.local.AudioVisualManagerLocal;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class AudioVisualManagerEJB extends BasePersistenceManagerEJB<AudioVisual> implements AudioVisualManagerLocal
{
	@Override
	public Class<AudioVisual> getModelClass()
	{
		return AudioVisual.class;
	}
}