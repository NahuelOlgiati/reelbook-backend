package com.reelbook.service.manager.local;

import javax.ejb.Local;

@Local
public interface YoutubeManagerLocal
{
	public abstract void getChannel(String refreshToken);

	public abstract void getSearch(String refreshToken);
}