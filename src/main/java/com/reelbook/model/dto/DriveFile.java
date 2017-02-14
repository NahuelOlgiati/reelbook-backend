package com.reelbook.model.dto;

public class DriveFile
{
	private String driveFileID;
	private String name;
	private String mimeType;

	public DriveFile(String driveFileID, String name, String mimeType)
	{
		this.driveFileID = driveFileID;
		this.name = name;
		this.mimeType = mimeType;
	}

	public DriveFile()
	{
		this(null, null, null);
	}

	public String getDriveFileID()
	{
		return driveFileID;
	}

	public void setDriveFileID(String driveFileID)
	{
		this.driveFileID = driveFileID;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getMimeType()
	{
		return mimeType;
	}

	public void setMimeType(String mimeType)
	{
		this.mimeType = mimeType;
	}
}
