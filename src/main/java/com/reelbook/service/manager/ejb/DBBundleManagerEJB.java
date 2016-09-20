package com.reelbook.service.manager.ejb;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.Query;
import com.reelbook.core.util.CompareUtil;
import com.reelbook.model.BundleKeyValue;
import com.reelbook.server.ejb.BaseEJB;
import com.reelbook.service.manager.local.DBBundleManagerLocal;

@Stateless
@EJB(name = "java:global/ejb/DBBundleManagerEJB", beanInterface = DBBundleManagerLocal.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class DBBundleManagerEJB extends BaseEJB implements DBBundleManagerLocal
{
	/**
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Object[][] getContents()
	{
		final Query createNativeQuery = em.createNativeQuery("SELECT * FROM  " + "adonis_common_bundlekeyvalue", "bundleKeyValueMap");
		final List<BundleKeyValue> list = createNativeQuery.getResultList();
		final Object[][] contents = new Object[list.size()][2];
		if (!CompareUtil.isEmpty(list))
		{
			for (int i = 0; i < list.size(); i++)
			{
				contents[i][0] = list.get(i).getKey();
				contents[i][1] = list.get(i).getValue();
			}
		}
		return contents;
	}
}