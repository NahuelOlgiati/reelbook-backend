package com.reelbook.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.hibernate.envers.Audited;
import com.google.gson.annotations.SerializedName;
import com.reelbook.core.exception.ValidationException;
import com.reelbook.core.model.BaseModel;

@Entity
@Table(name = "adonis_config_file")
@Audited
@SuppressWarnings("serial")
public class File extends BaseModel
{
	@Id
	@SerializedName(value = "id")
	@SequenceGenerator(name = "id", sequenceName = "adonis_config_file_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "id")
	private Long fileID;

	@Column(length = 255)
	private String fileName;

	@Lob
	private String content;

	/**
	 */
	public File(String fileName, String content)
	{
		this.fileID = 0l;
		this.fileName = fileName;
		this.content = content;
	}

	/**
	 */
	public File()
	{
		this(null, null);
	}

	/**
	 */
	@Override
	public Long getID()
	{
		return fileID;
	}

	/**
	 */
	@Override
	public void setID(Long id)
	{
		this.fileID = id;
	}

	public String getFileName()
	{
		return fileName;
	}

	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}

	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
	}

	/**
	 */
	@Override
	public void valid() throws ValidationException
	{
		// TODO Auto-generated method stub

	}
}