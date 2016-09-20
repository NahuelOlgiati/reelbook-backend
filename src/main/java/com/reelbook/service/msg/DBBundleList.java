package com.reelbook.service.msg;

import java.util.ListResourceBundle;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.reelbook.service.manager.local.DBBundleManagerLocal;

public class DBBundleList extends ListResourceBundle {
	/**
	 */
	@Override
	protected Object[][] getContents() {
		Object[][] contents = null;
		try {
			final InitialContext initialContext = new InitialContext();
			final DBBundleManagerLocal manager = (DBBundleManagerLocal) initialContext
					.lookup("java:global/ejb/DBBundleManagerEJB");
			contents = manager.getContents();
		} catch (final NamingException e) {
			// log.error(e.getMessage(), e);
		}
		return contents;
	}
}