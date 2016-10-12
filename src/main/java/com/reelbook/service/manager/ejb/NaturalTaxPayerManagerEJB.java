package com.reelbook.service.manager.ejb;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import com.reelbook.core.exception.BaseException;
import com.reelbook.core.model.support.QueryHint;
import com.reelbook.core.service.util.PredicateBuilder;
import com.reelbook.core.service.util.QueryHintResult;
import com.reelbook.core.util.CompareUtil;
import com.reelbook.model.DocumentType;
import com.reelbook.model.DocumentType_;
import com.reelbook.model.NaturalTaxPayer;
import com.reelbook.model.NaturalTaxPayer_;
import com.reelbook.model.embeddable.Document;
import com.reelbook.model.embeddable.Document_;
import com.reelbook.service.manager.local.NaturalTaxPayerManagerLocal;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class NaturalTaxPayerManagerEJB extends BaseTaxPayerManagerEJB<NaturalTaxPayer> implements NaturalTaxPayerManagerLocal
{
	/**
	 */
	@Override
	public Class<NaturalTaxPayer> getModelClass()
	{
		return NaturalTaxPayer.class;
	}

	/**
	 */
	@Override
	public QueryHintResult<NaturalTaxPayer> getQueryHintResult(final String description, final QueryHint queryHint)
	{
		QueryHintResult<NaturalTaxPayer> queryHintResult = null;
		try
		{
			final CriteriaBuilder cb = em.getCriteriaBuilder();
			final PredicateBuilder pb = new PredicateBuilder(cb);
			final CriteriaQuery<NaturalTaxPayer> cq = cb.createQuery(getModelClass());
			final Root<NaturalTaxPayer> naturalTaxPayer = cq.from(getModelClass());
			final Path<String> lastName = naturalTaxPayer.get(NaturalTaxPayer_.lastName);
			final Path<String> firstName = naturalTaxPayer.get(NaturalTaxPayer_.firstName);
			final Path<Document> document = naturalTaxPayer.get("document");
			final Path<String> documentNumber = document.get(Document_.documentNumber);
			final List<Order> orderList = new ArrayList<Order>();

			// Expressions.
			cq.where(cb.or(pb.like(lastName, description), pb.like(firstName, description), pb.like(documentNumber, description)));
			orderList.add(cb.asc(lastName));
			orderList.add(cb.asc(firstName));
			cq.orderBy(orderList);

			// Gets data.
			queryHintResult = getQueryHintResult(cq, queryHint);
		}
		catch (final Throwable t)
		{
			throw new EJBException(t.getMessage());
		}
		return queryHintResult;
	}

	/**
	 */
	@Override
	public List<NaturalTaxPayer> getNaturalTaxPayerByDocument(final Document document, final QueryHint queryHint)
	{
		List<NaturalTaxPayer> naturalTaxPayerList = null;
		try
		{
			final CriteriaBuilder cb = em.getCriteriaBuilder();
			final PredicateBuilder pb = new PredicateBuilder(cb);
			final CriteriaQuery<NaturalTaxPayer> cq = cb.createQuery(getModelClass());
			final Root<NaturalTaxPayer> naturalTaxPayer = cq.from(getModelClass());

			final Path<Document> pd = naturalTaxPayer.get("document");
			final Path<String> pdDocumentNumber = pd.get(Document_.documentNumber);
			final Path<DocumentType> pdDocumentType = pd.get(Document_.documentType);
			final Path<String> pdDocumentTypeDesc = pdDocumentType.get(DocumentType_.description);

			final Path<Document> sd = naturalTaxPayer.get("secondaryDocument");
			final Path<String> sdDocumentNumber = sd.get(Document_.documentNumber);
			final Path<DocumentType> sdDocumentType = sd.get(Document_.documentType);
			final Path<String> sdDocumentTypeDesc = sdDocumentType.get(DocumentType_.description);

			// Expressions.
			cq.distinct(Boolean.TRUE);
			cq.where(cb.or(
					cb.and(pb.equal(pdDocumentNumber, document.getDocumentNumber()),
							pb.equal(pdDocumentTypeDesc, document.getDocumentType().getDescription())),
					cb.and(pb.equal(sdDocumentNumber, document.getDocumentNumber()),
							pb.equal(sdDocumentTypeDesc, document.getDocumentType().getDescription()))));

			// Gets data.
			naturalTaxPayerList = getList(cq, queryHint);
		}
		catch (final Throwable t)
		{
			throw new EJBException(t.getMessage());
		}
		return naturalTaxPayerList;
	}

	/**
	 */
	@Override
	protected void doBeforeAddUpdate(final NaturalTaxPayer model) throws BaseException
	{
		try
		{
			model.getDocument().valid();
		}
		catch (final Throwable t)
		{
			return;
		}

		if ((!CompareUtil.isEmpty(model.getSecondaryDocumentRO())) && (model.getDocument().equals(model.getSecondaryDocumentRO())))
		{
			// throw new ManagerException(DBSMsgHandler.getMsg(NaturalTaxPayerManagerEJB.class, "documentEqualToSecondaryDocument"));
		}

		if (getDuplicatedException(model.getDocument(), model))
		{
			// throw new ManagerException(DBSMsgHandler.getMsg(NaturalTaxPayerManagerEJB.class, "duplicatedDocument"));
		}

		if (getDuplicatedException(model.getSecondaryDocumentRO(), model))
		{
			// throw new ManagerException(DBSMsgHandler.getMsg(NaturalTaxPayerManagerEJB.class, "duplicatedSecondaryDocument"));
		}

		super.doBeforeAddUpdate(model);
	}

	/**
	 */
	@Override
	public boolean getDuplicatedException(final Document document, final NaturalTaxPayer naturalTaxPayer)
	{
		Boolean duplicated = Boolean.FALSE;
		try
		{
			if (!CompareUtil.isEmpty(document))
			{
				final List<NaturalTaxPayer> ntpList = getNaturalTaxPayerByDocument(document, new QueryHint(0, 1));

				if (!CompareUtil.isEmpty(ntpList))
				{
					duplicated = !ntpList.contains(naturalTaxPayer);
				}
			}
		}
		catch (final Throwable t)
		{
			throw new EJBException(t.getMessage());
		}
		return duplicated;
	}
}