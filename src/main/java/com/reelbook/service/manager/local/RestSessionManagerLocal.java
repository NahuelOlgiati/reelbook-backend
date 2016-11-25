package com.reelbook.service.manager.local;

import javax.ejb.Local;
import com.reelbook.core.exception.ManagerException;
import com.reelbook.core.service.manager.local.BasePersistenceManager;
import com.reelbook.model.RestSession;
import com.reelbook.model.User;

@Local
public interface RestSessionManagerLocal extends BasePersistenceManager<RestSession>
{
	public RestSession find(String token);

	public String register(User user, String agent, String IP, Boolean expires) throws ManagerException;

	public void deleteTokensForUser(Long userID);

	public void deleteTokens(Integer inectiveSeconds, Boolean expires);
}