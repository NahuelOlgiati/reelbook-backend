package com.reelbook.service.manager.ejb;

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

import com.reelbook.model.DocumentType;
import com.reelbook.model.DocumentType_;
import com.reelbook.server.ejb.BasePersistenceManagerEJB;
import com.reelbook.server.model.support.QueryHint;
import com.reelbook.server.util.PredicateBuilder;
import com.reelbook.server.util.QueryHintResult;
import com.reelbook.service.manager.local.DocumentTypeManagerLocal;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class DocumentTypeManagerEJB extends BasePersistenceManagerEJB<DocumentType> implements DocumentTypeManagerLocal
{
	/**
	 */
	@Override
	public Class<DocumentType> getModelClass()
	{
		return DocumentType.class;
	}

	/**
	 */
	@Override
	public QueryHintResult<DocumentType> getQueryHintResult(final String description, final QueryHint queryHint)
	{
		QueryHintResult<DocumentType> queryHintResult = null;
		try
		{
			final CriteriaBuilder cb = em.getCriteriaBuilder();
			final PredicateBuilder pb = new PredicateBuilder(cb);
			final CriteriaQuery<DocumentType> cq = cb.createQuery(getModelClass());
			final Root<DocumentType> documentType = cq.from(getModelClass());
			final Path<String> dtDescription = documentType.get(DocumentType_.description);
			final Path<String> dtSummaryDescription = documentType.get(DocumentType_.summaryDescription);

			// Expessions.
			cq.where(cb.or(pb.like(dtDescription, description), pb.like(dtSummaryDescription, description)));
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