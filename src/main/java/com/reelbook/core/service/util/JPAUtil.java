package com.reelbook.core.service.util;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

public class JPAUtil
{
	private static volatile long aliasCount = 0;

	/** 
	 */
	public static Integer count(EntityManager em, CriteriaQuery<?> criteria)
	{
		return em.createQuery(countCriteria(em.getCriteriaBuilder(), criteria)).getSingleResult().intValue();
	}

	/**
	 */
	public static CriteriaQuery<Long> countCriteria(CriteriaBuilder builder, CriteriaQuery<?> criteria)
	{
		CriteriaQuery<Long> countCriteria = builder.createQuery(Long.class);
		copyCriteriaNoSelection(criteria, countCriteria);

		Expression<?> expression = getExpressionToCount(criteria, countCriteria);
		if (criteria.isDistinct())
		{
			countCriteria.select(builder.countDistinct(expression));
		}
		else
		{
			countCriteria.select(builder.count(expression));
		}

		return countCriteria;
	}

	/**
	 */
	public static Expression<?> getExpressionToCount(CriteriaQuery<?> criteria, CriteriaQuery<Long> countCriteria)
	{
		Expression<?> expression;
		Selection<?> selection = criteria.getSelection();
		if (selection == null)
		{
			expression = findRoot(countCriteria, criteria.getResultType());
		}
		else
		{
			if (selection.isCompoundSelection())
			{
				expression = (Expression<?>) selection.getCompoundSelectionItems().get(0);
			}
			else
			{
				expression = (Expression<?>) selection;
			}
		}
		return expression;
	}

	/**
	 */
	public static void copyCriteriaNoSelection(CriteriaQuery<?> from, CriteriaQuery<?> to)
	{
		for (Root<?> root : from.getRoots())
		{
			Root<?> dest = to.from(root.getJavaType());
			dest.alias(getOrCreateAlias(root));
			copyJoins(root, dest, Boolean.FALSE);
		}
		to.groupBy(from.getGroupList());
		to.having(from.getGroupRestriction());
		to.where(from.getRestriction());
	}

	/**
	 */
	public static Root<?> findRoot(CriteriaQuery<?> query, Class<?> clazz)
	{
		for (Root<?> r : query.getRoots())
		{
			if (clazz.equals(r.getJavaType()))
			{
				return (Root<?>) r.as(clazz);
			}
		}
		return query.getRoots().iterator().next();
	}

	/**
	 */
	public static void copyJoins(From<?, ?> from, From<?, ?> to, Boolean copyFetches)
	{
		for (Join<?, ?> j : from.getJoins())
		{
			Join<?, ?> toJoin = to.join(j.getAttribute().getName(), j.getJoinType());
			toJoin.alias(getOrCreateAlias(j));
			copyJoins(j, toJoin, copyFetches);

			if (copyFetches)
			{
				for (Fetch<?, ?> f : from.getFetches())
				{
					Fetch<?, ?> toFetch = to.fetch(f.getAttribute().getName());
					copyFetches(f, toFetch);
				}
			}
		}
	}

	/**
	 */
	public static void copyFetches(Fetch<?, ?> from, Fetch<?, ?> to)
	{
		for (Fetch<?, ?> f : from.getFetches())
		{
			Fetch<?, ?> toFetch = to.fetch(f.getAttribute().getName());
			copyFetches(f, toFetch);
		}
	}

	/**
	 */
	public static synchronized String getOrCreateAlias(Selection<?> selection)
	{
		if (aliasCount > 1000)
			aliasCount = 0;

		String alias = selection.getAlias();
		if (alias == null)
		{
			alias = "JDAL_generatedAlias" + aliasCount++;
			selection.alias(alias);
		}
		return alias;
	}
}