package com.reelbook.service.manager.ejb;

import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import com.reelbook.core.exception.ManagerException;
import com.reelbook.core.service.manager.ejb.BasePersistenceManagerEJB;
import com.reelbook.core.service.util.PredicateBuilder;
import com.reelbook.model.RestSession;
import com.reelbook.model.RestSession_;
import com.reelbook.model.User;
import com.reelbook.model.User_;
import com.reelbook.rest.app.UserPrincipalMap;
import com.reelbook.service.manager.local.RestSessionManagerLocal;
import com.reelbook.service.manager.local.UserManagerLocal;

@Stateless
@EJB(name = "java:global/ejb/RestSessionManagerEJB", beanInterface = RestSessionManagerLocal.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class RestSessionManagerEJB extends BasePersistenceManagerEJB<RestSession> implements RestSessionManagerLocal
{
	@EJB
	private UserManagerLocal userML;

	@Override
	public Class<RestSession> getModelClass()
	{
		return RestSession.class;
	}

	@Override
	public RestSession find(String token)
	{
		RestSession model = null;

		final CriteriaBuilder cb = em.getCriteriaBuilder();
		final PredicateBuilder pb = new PredicateBuilder(cb);
		final CriteriaQuery<RestSession> cq = cb.createQuery(getModelClass());
		final Root<RestSession> restSession = cq.from(getModelClass());

		final Path<String> tK = restSession.get(RestSession_.token);
		cq.where(pb.like(tK, token));

		try
		{
			final TypedQuery<RestSession> tq = em.createQuery(cq);
			model = tq.getSingleResult();
		}
		catch (NoResultException n)
		{

		}
		return model;
	}

	@Override
	public String register(User user, String agent, String ip, Boolean expires) throws ManagerException
	{
		RestSession rSession = this.find(user, agent, ip);

		if (rSession == null)
		{
			rSession = new RestSession(user, agent, ip, expires);
			rSession = this.save(rSession);
			user = userML.get(user.getID());
			UserPrincipalMap.put(rSession.getToken(), user);
		}

		return rSession.getToken();
	}

	@Override
	public void deleteTokensForUser(Long userID)
	{
		List<RestSession> restSessionList = null;

		final CriteriaBuilder cb = em.getCriteriaBuilder();
		final CriteriaQuery<RestSession> cq = cb.createQuery(getModelClass());
		final Root<RestSession> restSession = cq.from(getModelClass());
		final Path<User> user = restSession.get(RestSession_.user);
		final Path<Long> id = user.get(User_.userID);

		cq.where(cb.equal(id, userID));

		try
		{
			final TypedQuery<RestSession> tq = em.createQuery(cq);
			restSessionList = tq.getResultList();
			for (RestSession rS : restSessionList)
			{
				this.delete(rS.getID());
			}
		}
		catch (ManagerException n)
		{
		}
		catch (NoResultException n)
		{
		}
	}

	@Override
	public void deleteTokens(Integer inectiveSeconds, Boolean expires)
	{
		List<RestSession> restSessionList = null;

		final CriteriaBuilder cb = em.getCriteriaBuilder();
		final CriteriaQuery<RestSession> cq = cb.createQuery(getModelClass());
		final Root<RestSession> restSession = cq.from(getModelClass());
		final Path<Boolean> exp = restSession.get(RestSession_.expires);
		final Path<Date> lastAccess = restSession.get(RestSession_.lastAccess);

		final Date limitTime = new Date((new Date()).getTime() - (inectiveSeconds * 1000));

		cq.where(cb.equal(exp, expires), cb.lessThan(lastAccess, limitTime));

		try
		{
			final TypedQuery<RestSession> tq = em.createQuery(cq);
			restSessionList = tq.getResultList();
			for (RestSession rS : restSessionList)
			{
				this.delete(rS.getID());
			}
		}
		catch (ManagerException n)
		{
		}
		catch (NoResultException n)
		{
		}
	}

	private RestSession find(User user, String agent, String ip)
	{
		RestSession model = null;

		final CriteriaBuilder cb = em.getCriteriaBuilder();
		final PredicateBuilder pb = new PredicateBuilder(cb);
		final CriteriaQuery<RestSession> cq = cb.createQuery(getModelClass());
		final Root<RestSession> restSession = cq.from(getModelClass());

		final Path<User> userP = restSession.get(RestSession_.user);
		final Path<Long> userIDP = userP.get(User_.userID);
		final Path<String> agentP = restSession.get(RestSession_.agent);
		final Path<String> ipP = restSession.get(RestSession_.IP);
		cq.where(pb.equal(userIDP, user.getID()), pb.equal(agentP, agent), pb.equal(ipP, ip));

		try
		{
			final TypedQuery<RestSession> tq = em.createQuery(cq);
			model = tq.getSingleResult();
		}
		catch (NoResultException n)
		{

		}
		return model;
	}
}