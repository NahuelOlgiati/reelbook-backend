package com.reelbook.service.manager.local;

import javax.ejb.Local;
import com.reelbook.core.service.manager.local.BasePersistenceManager;
import com.reelbook.model.File;

@Local
public interface FileManagerLocal extends BasePersistenceManager<File>
{
}