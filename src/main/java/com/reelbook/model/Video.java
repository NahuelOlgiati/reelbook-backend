package com.reelbook.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import org.hibernate.envers.Audited;
import com.google.gson.annotations.SerializedName;
import com.reelbook.core.exception.ValidationException;
import com.reelbook.core.model.BaseModel;

@Entity
@Table(name = "video")
@Audited
@XmlAccessorType(XmlAccessType.FIELD)
@SuppressWarnings("serial")
public class Video extends BaseModel
{
	@Id
	@SerializedName(value = "id")
	@SequenceGenerator(name = "id", sequenceName = "video_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "id")
	private Long videoID;

	@Column(length = 255)
	private String fileName;

	@Lob
	private byte[] content;

	@Basic
	@Column(name = "content", insertable = false, updatable = false)
	private Long oID;

	public Video(String fileName, byte[] content)
	{
		this.videoID = 0l;
		this.fileName = fileName;
		this.content = content;
	}

	public Video()
	{
		this(null, null);
	}

	@Override
	public Long getID()
	{
		return videoID;
	}

	@Override
	public void setID(Long id)
	{
		this.videoID = id;
	}

	public String getFileName()
	{
		return fileName;
	}

	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}

	public byte[] getContent()
	{
		return content;
	}

	public void setContent(byte[] content)
	{
		this.content = content;
	}

	public Long getoID()
	{
		return oID;
	}

	public void setoID(Long oID)
	{
		this.oID = oID;
	}

	@Override
	public void valid() throws ValidationException
	{
		// TODO Auto-generated method stub

	}
}