package com.reelbook.core.service.manager.ejb;

import java.util.List;
import javax.ejb.EJBException;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import com.reelbook.core.model.BaseModel;
import com.reelbook.core.model.support.QueryHint;
import com.reelbook.core.service.manager.local.BaseManager;
import com.reelbook.core.service.util.JPAUtil;
import com.reelbook.core.service.util.QueryHintResult;
import com.reelbook.core.util.CompareUtil;

public abstract class BaseManagerEJB<T extends BaseModel> extends BaseEJB implements BaseManager<T>
{
	/**
	 */
	@Override
	public T get(final Long modelID)
	{
		T model = null;
		try
		{
			model = em.find(getModelClass(), modelID);
		}
		catch (Throwable t)
		{
			throw new EJBException(t.getMessage());
		}
		return model;
	}

	/**
	 */
	@Override
	public T getFULL(final Long modelID)
	{
		T model = null;
		try
		{
			if (!CompareUtil.isEmpty(model = get(modelID)))
			{
				model.initLazyElements();
			}
		}
		catch (Throwable t)
		{
			throw new EJBException(t.getMessage());
		}
		return model;
	}

	/**
	 */
	protected final <X> X getUnique(final CriteriaQuery<X> criteriaQuery)
	{
		X model = null;
		try
		{
			final TypedQuery<X> tq = em.createQuery(criteriaQuery);
			model = tq.getSingleResult();
		}
		catch (NoResultException n)
		{
		}
		catch (Throwable t)
		{
			throw new EJBException(t.getMessage());
		}
		return model;
	}

	/**
	 */
	protected final <X> List<X> getList(final CriteriaQuery<X> criteriaQuery, final QueryHint queryHint)
	{
		List<X> modelList = null;
		try
		{
			final TypedQuery<X> tq = em.createQuery(criteriaQuery);
			tq.setFirstResult(queryHint.getFirstResult());
			tq.setMaxResults(queryHint.getMaxResults());
			modelList = tq.getResultList();
		}
		catch (Throwable t)
		{
			throw new EJBException(t.getMessage());
		}
		return modelList;
	}

	/**
	 */
	protected final <X> QueryHintResult<X> getQueryHintResult(final CriteriaQuery<X> criteriaQuery, final QueryHint queryHint)
	{
		QueryHintResult<X> queryHintResult = null;

		List<X> list = getList(criteriaQuery, queryHint);

		Integer rowCount = 0;
		if (queryHint.isCountResults())
		{
			rowCount = JPAUtil.count(em, criteriaQuery);
		}

		queryHintResult = new QueryHintResult<X>(rowCount, list);

		return queryHintResult;
	}

	/**
	 */
	protected final <X> List<X> getList(final CriteriaQuery<X> criteriaQuery)
	{
		List<X> modelList = null;
		try
		{
			final TypedQuery<X> tq = em.createQuery(criteriaQuery);
			modelList = tq.getResultList();
		}
		catch (Throwable t)
		{
			throw new EJBException(t.getMessage());
		}
		return modelList;
	}
}