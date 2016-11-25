package com.reelbook.service.manager.local;

import javax.ejb.Local;
import com.reelbook.core.model.support.QueryHint;
import com.reelbook.core.service.util.QueryHintResult;
import com.reelbook.model.User;
import com.reelbook.service.manager.support.UserQP;

@Local
public interface UserManagerLocal extends BaseUserManagerLocal<User>
{
	public QueryHintResult<User> getQueryHintResult(UserQP qp, QueryHint queryHint);

	public Boolean hasAdminPermission(User user);

	public User getUser(String uName);
}