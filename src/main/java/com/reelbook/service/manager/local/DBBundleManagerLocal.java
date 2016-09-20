package com.reelbook.service.manager.local;

import javax.ejb.Local;

@Local
public interface DBBundleManagerLocal {
	/**
	 */
	public abstract Object[][] getContents();
}