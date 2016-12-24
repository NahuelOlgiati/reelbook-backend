package com.reelbook.service.manager.ejb;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import com.reelbook.core.exception.BaseException;
import com.reelbook.core.model.support.QueryHint;
import com.reelbook.core.service.manager.ejb.BasePersistenceManagerEJB;
import com.reelbook.core.service.util.PredicateBuilder;
import com.reelbook.core.service.util.QueryHintResult;
import com.reelbook.model.Artist;
import com.reelbook.model.Artist_;
import com.reelbook.model.User;
import com.reelbook.service.manager.local.ArtistManagerLocal;
import com.reelbook.service.manager.local.UserManagerLocal;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ArtistManagerEJB extends BasePersistenceManagerEJB<Artist> implements ArtistManagerLocal
{
	@EJB
	private UserManagerLocal userML;
	
	@Override
	public Class<Artist> getModelClass()
	{
		return Artist.class;
	}
	
	@Override
	protected void doAfterAdd(Artist model) throws BaseException 
	{
		super.doAfterAdd(model);
		User user = userML.get(model.getUserID());
		user.setArtistID(model.getID());
	}

	@Override
	public QueryHintResult<Artist> getQueryHintResult(final String description, final QueryHint queryHint)
	{
		QueryHintResult<Artist> queryHintResult = null;
		try
		{
			final CriteriaBuilder cb = em.getCriteriaBuilder();
			final PredicateBuilder pb = new PredicateBuilder(cb);
			final CriteriaQuery<Artist> cq = cb.createQuery(getModelClass());
			final Root<Artist> artist = cq.from(getModelClass());
			final Path<String> dtDescription = artist.get(Artist_.description);

			// Expessions.
			cq.where(cb.or(pb.like(dtDescription, description)));
			cq.orderBy(cb.asc(dtDescription));

			// Gets data.
			queryHintResult = getQueryHintResult(cq, queryHint);
		}
		catch (Throwable t)
		{
			throw new EJBException(t.getMessage());
		}
		return queryHintResult;
	}
	
	@Override
	public QueryHintResult<Artist> getQueryHintResult(final List<String> descriptionList, final QueryHint queryHint)
	{
		QueryHintResult<Artist> queryHintResult = null;
		try
		{
			final CriteriaBuilder cb = em.getCriteriaBuilder();
			final PredicateBuilder pb = new PredicateBuilder(cb);
			final CriteriaQuery<Artist> cq = cb.createQuery(getModelClass());
			final Root<Artist> artist = cq.from(getModelClass());
			final Path<String> dtDescription = artist.get(Artist_.description);

			// Expessions.
			cq.where(cb.or(pb.in(dtDescription, descriptionList)));
			cq.orderBy(cb.asc(dtDescription));

			// Gets data.
			queryHintResult = getQueryHintResult(cq, queryHint);
		}
		catch (Throwable t)
		{
			throw new EJBException(t.getMessage());
		}
		return queryHintResult;
	}
}