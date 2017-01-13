package com.reelbook.service.manager.ejb;

import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import com.reelbook.core.service.manager.ejb.BaseEJB;
import com.reelbook.core.service.util.PredicateBuilder;
import com.reelbook.model.PGLargeObject;
import com.reelbook.model.PGLargeObject_;
import com.reelbook.service.manager.local.PGLargeObjectManagerLocal;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class PGLargeObjectManagerEJB extends BaseEJB implements PGLargeObjectManagerLocal
{
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getList(Long oID)
	{
		List<Object[]> list = null;
		try
		{		
			final Query createNativeQuery = em.createNativeQuery("SELECT loID, pageNo, data FROM  pg_largeobject WHERE loid=" + oID.toString()+ " ORDER BY pageNo ASC");
			list = createNativeQuery.getResultList();
			
//			final EntityManager em = Persistence.createEntityManagerFactory("pg_catalog").createEntityManager();
//			final CriteriaBuilder cb = em.getCriteriaBuilder();
//			final PredicateBuilder pb = new PredicateBuilder(cb);
//			final CriteriaQuery<PGLargeObject> cq = cb.createQuery(PGLargeObject.class);
//			final Root<PGLargeObject> pgLargeObject = cq.from(PGLargeObject.class);
//			final Path<Long> loID = pgLargeObject.get(PGLargeObject_.loID);
//			final Path<Integer> pageNo = pgLargeObject.get(PGLargeObject_.pageNo);
//
//			// Expressions.
//			cq.where(cb.equal(loID, oID));
//			cq.orderBy(cb.asc(pageNo));
//
//			// Gets data.
//			final TypedQuery<PGLargeObject> tq = em.createQuery(cq);
//			list = tq.getResultList();
		}
		catch (final Throwable t)
		{
			throw new EJBException(t.getMessage());
		}
		return list;
	}
}