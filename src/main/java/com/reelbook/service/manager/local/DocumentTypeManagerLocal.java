package com.reelbook.service.manager.local;

import javax.ejb.Local;
import com.reelbook.core.service.manager.local.BasePersistenceManager;
import com.reelbook.core.service.manager.local.BaseSimpleManager;
import com.reelbook.model.DocumentType;

@Local
public interface DocumentTypeManagerLocal extends BasePersistenceManager<DocumentType>, BaseSimpleManager<DocumentType>
{
}