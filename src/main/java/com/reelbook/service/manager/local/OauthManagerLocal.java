package com.reelbook.service.manager.local;

import javax.ejb.Local;

@Local
public interface OauthManagerLocal
{
	public abstract void saveYoutubeCredential(Long userID, String authCode, String redirectUri);

	public abstract void saveDriveCredential(Long userID, String code, String redirectUri);
}