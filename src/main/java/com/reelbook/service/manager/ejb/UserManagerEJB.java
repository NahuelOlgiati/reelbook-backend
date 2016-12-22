package com.reelbook.service.manager.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import com.reelbook.core.model.support.QueryHint;
import com.reelbook.core.service.util.PredicateBuilder;
import com.reelbook.core.service.util.QueryHintResult;
import com.reelbook.core.util.CompareUtil;
import com.reelbook.model.User;
import com.reelbook.model.User_;
import com.reelbook.service.manager.local.UserManagerLocal;
import com.reelbook.service.manager.support.UserQP;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class UserManagerEJB extends BaseUserManagerEJB<User> implements UserManagerLocal
{
	@Override
	public Class<User> getModelClass()
	{
		return User.class;
	}

	@Override
	public QueryHintResult<User> getQueryHintResult(final UserQP qp, final QueryHint queryHint)
	{
		final CriteriaBuilder cb = em.getCriteriaBuilder();
		final PredicateBuilder pb = new PredicateBuilder(cb);
		final CriteriaQuery<User> cq = cb.createQuery(getModelClass());
		final Root<User> user = cq.from(getModelClass());
		final Path<String> firstName = user.get(User_.firstName);
		final Path<String> lastName = user.get(User_.lastName);
		final Path<String> userName = user.get(User_.userName);
		final Path<String> email = user.get(User_.email);
		final Path<Boolean> active = user.get(User_.active);

		// Expressions.
		cq.where(pb.like(firstName, qp.getFirstName()), pb.like(lastName, qp.getLastName()), pb.like(userName, qp.getUserName()),
				pb.like(email, qp.getEmail()), pb.equal(active, qp.getActive()));
		cq.orderBy(cb.asc(lastName), cb.asc(firstName));

		// Gets data.
		return getQueryHintResult(cq, queryHint);
	}

	@Override
	public Boolean hasAdminPermission(final User user)
	{
		Boolean result = Boolean.FALSE;
		if (!CompareUtil.isEmpty(user))
		{
			final CriteriaBuilder cb = em.getCriteriaBuilder();
			final CriteriaQuery<String> cq = cb.createQuery(String.class);
			final Root<User> userPath = cq.from(getModelClass());
			// final ListJoin<User, EnabledRole> enabledRole = userPath.join(User_.enabledRoles, JoinType.LEFT);
			// final Join<EnabledRole, Role> role = enabledRole.join(EnabledRole_.role, JoinType.LEFT);
			// final ListJoin<Role, String> permission = role.join(Role_.permissions, JoinType.LEFT);

			// Expressions.
			// cq.select(permission);
			// cq.where(cb.equal(userPath, user), cb.equal(permission, "ADMIN"));

			// Gets data.
			final List<String> permissions = getList(cq, new QueryHint(0, 1, false));

			result = !CompareUtil.isEmpty(permissions);
		}
		return result;
	}

	@Override
	public User getUser(String uName)
	{
		final CriteriaBuilder cb = em.getCriteriaBuilder();
		final PredicateBuilder pb = new PredicateBuilder(cb);
		final CriteriaQuery<User> cq = cb.createQuery(getModelClass());
		final Root<User> user = cq.from(getModelClass());
		final Path<String> userName = user.get(User_.userName);

		cq.where(pb.like(userName, uName));

		return getUnique(cq);
	}
}