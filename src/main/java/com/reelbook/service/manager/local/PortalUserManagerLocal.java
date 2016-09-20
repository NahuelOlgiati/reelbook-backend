package com.reelbook.service.manager.local;

import javax.ejb.Local;

import com.reelbook.model.PortalUser;
import com.reelbook.model.embeddable.Document;
import com.reelbook.model.enumeration.TaxPayerTypeEnum;
import com.reelbook.server.exception.ManagerException;


@Local
public interface PortalUserManagerLocal extends BaseUserManagerLocal<PortalUser>
{
	/**
	 */
	public abstract PortalUser register(final PortalUser portalUser, final Document document, final TaxPayerTypeEnum taxPayerType)
			throws ManagerException;

	/**
	 */
	public abstract void changeMailAddress(PortalUser portalUser, String newEmailAddress) throws ManagerException;

	/**
	 */
	public abstract void sendPassword(final Document document) throws ManagerException;
}