package com.reelbook.service.manager.local;

import java.util.List;
import javax.ejb.Local;

@Local
public interface PGLargeObjectManagerLocal
{
	public abstract List<Object[]> getList(Long getoID);

	public abstract Long getCount(Long oID);
}