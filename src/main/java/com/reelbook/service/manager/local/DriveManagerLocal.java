package com.reelbook.service.manager.local;

import java.util.List;
import javax.ejb.Local;
import com.reelbook.model.dto.DriveFile;

@Local
public interface DriveManagerLocal
{
	public abstract List<DriveFile> getUserFiles(Long userID);
}