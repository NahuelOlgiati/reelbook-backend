package com.reelbook.service.manager.ejb;

import javax.ejb.EJBException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import com.reelbook.core.service.manager.ejb.BasePersistenceManagerEJB;
import com.reelbook.core.service.util.PredicateBuilder;
import com.reelbook.model.DocumentType;
import com.reelbook.model.DocumentType_;
import com.reelbook.model.embeddable.Document;
import com.reelbook.model.embeddable.Document_;
import com.reelbook.model.msc.Agent;
import com.reelbook.service.manager.local.AgentManagerLocal;

public abstract class AgentManagerEJB<T extends Agent> extends BasePersistenceManagerEJB<T> implements AgentManagerLocal<T>
{
	/**
	 */
	@Override
	public T get(final Document d)
	{
		T model = null;
		try
		{
			final CriteriaBuilder cb = em.getCriteriaBuilder();
			final PredicateBuilder pb = new PredicateBuilder(cb);
			final CriteriaQuery<T> cq = cb.createQuery(getModelClass());
			final Root<T> agent = cq.from(getModelClass());
			final Path<Document> document = agent.get("document");
			final Path<DocumentType> documentType = document.get(Document_.documentType);
			final Path<Long> documentTypeID = documentType.get(DocumentType_.documentTypeID);
			final Path<String> documentNumber = document.get(Document_.documentNumber);

			// Expressions.
			cq.where(cb.and(pb.equal(documentTypeID, d.getDocumentType().getID()), pb.equal(documentNumber, d.getDocumentNumber())));

			// Gets data.
			model = getUnique(cq);
		}
		catch (Throwable t)
		{
			throw new EJBException(t.getMessage());
		}
		return model;
	}
}