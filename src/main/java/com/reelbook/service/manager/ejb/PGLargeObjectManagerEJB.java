package com.reelbook.service.manager.ejb;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.Query;
import com.reelbook.core.service.manager.ejb.BaseEJB;
import com.reelbook.service.manager.local.PGLargeObjectManagerLocal;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class PGLargeObjectManagerEJB extends BaseEJB implements PGLargeObjectManagerLocal
{
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getList(Long oID)
	{
		List<Object> list = null;
		try
		{
			String select = "SELECT data FROM  pg_largeobject WHERE loid=" + oID.toString();
			String orderBy = " ORDER BY pageNo ASC";
			final Query createNativeQuery = em.createNativeQuery(select + orderBy);
			list = createNativeQuery.getResultList();
		}
		catch (final Throwable t)
		{
			throw new EJBException(t.getMessage());
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getList(Long oID, Integer numberOfRows, Integer page)
	{
		List<Object> list = null;
		try
		{
			String select = "SELECT data FROM  pg_largeobject WHERE loid=" + oID.toString();
			String orderBy = " ORDER BY pageNo ASC";
			String queryHint = " LIMIT " + numberOfRows + " OFFSET " + page * numberOfRows;
			final Query createNativeQuery = em.createNativeQuery(select + orderBy + queryHint);
			list = createNativeQuery.getResultList();
		}
		catch (final Throwable t)
		{
			throw new EJBException(t.getMessage());
		}
		return list;
	}

	@Override
	public Long getCount(Long oID)
	{
		Long count = null;
		try
		{
			count = ((Number) em.createNativeQuery("SELECT count(*) FROM  pg_largeobject WHERE loid=" + oID.toString()).getSingleResult())
					.longValue();
		}
		catch (final Throwable t)
		{
			throw new EJBException(t.getMessage());
		}
		return count;
	}
	
//	private static final int ONE_ROW = 2048;
//	
//	@Override
//	public Long getFileSize(Long oID, Long totalSegments)
//	{
//		Long count = null;
//		try
//		{
//			count = ((Number) em.createNativeQuery("SELECT count(*) FROM  pg_largeobject WHERE loid=" + oID.toString()).getSingleResult())
//					.longValue();
//		}
//		catch (final Throwable t)
//		{
//			throw new EJBException(t.getMessage());
//		}
//		return count;
//	}
	
//	octet_length
//	
//	Long count = pgLargeObjectML.getCount(video.getoID());
//	Integer totalSegments = BigDecimal.valueOf(count).divide(BigDecimal.valueOf(NUMBER_OF_ROWS), 0, RoundingMode.UP).intValue();
}