package com.reelbook.service.manager.local;

import javax.ejb.Local;

import com.reelbook.model.DocumentType;
import com.reelbook.server.ejb.BasePersistenceManager;
import com.reelbook.server.ejb.BaseSimpleManager;

@Local
public interface DocumentTypeManagerLocal
		extends BasePersistenceManager<DocumentType>, BaseSimpleManager<DocumentType> {
}