package com.reelbook.service.manager.local;

import java.util.List;
import com.reelbook.core.exception.ManagerException;
import com.reelbook.core.model.support.QueryHint;
import com.reelbook.core.service.manager.local.BasePersistenceManager;
import com.reelbook.core.service.manager.local.BaseSimpleManager;
import com.reelbook.core.service.util.QueryHintResult;
import com.reelbook.model.Profile;
import com.reelbook.model.User;
import com.reelbook.model.embeddable.Document;

public interface BaseUserManagerLocal<T extends User> extends BasePersistenceManager<T>, BaseSimpleManager<T>
{

	public abstract T getCurrent();

	public abstract T get(final String userName);

	public abstract T getFULL(String userName);

	public abstract T get(final Document d);

	public abstract List<T> getList(final Profile profile);

	public abstract QueryHintResult<T> getQueryHintResult(final Boolean excludeReserved, final Boolean active, final String description,
			final QueryHint queryHint);

	public abstract QueryHintResult<T> getQueryHintResult(final Boolean active, final String description, final QueryHint queryHint);

	public abstract void validSystemAgentDuplication(Document document) throws ManagerException;
}