package com.reelbook.model;

import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import org.hibernate.envers.Audited;
import com.reelbook.core.exception.ValidationException;
import com.reelbook.core.model.BaseModel;
import com.reelbook.core.msg.MessageBuilder;

@Entity
@Table(name = "audiovisual")
@Audited
@Cacheable(value = true)
@XmlAccessorType(XmlAccessType.FIELD)
@SuppressWarnings("serial")
public class AudioVisual extends BaseModel
{
	@Id
	@SequenceGenerator(name = "id", sequenceName = "audiovisual_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "id")
	private Long audiovisualID;

	@Basic
	private Long userID;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "videoID")
	private Video video;

	public AudioVisual(Long userID)
	{
		this.audiovisualID = 0l;
		this.userID = userID;
		this.video = null;
	}
	
	public AudioVisual()
	{
		this(null);
	}

	@Override
	public Long getID()
	{
		return audiovisualID;
	}

	@Override
	public void setID(Long id)
	{
		this.audiovisualID = id;
	}

	public Long getUserID()
	{
		return userID;
	}

	public void setUserID(Long id)
	{
		this.userID = id;
	}
	
	public Video getVideo()
	{
		return video;
	}

	public void setVideo(Video video)
	{
		this.video = video;
	}

	@Override
	public void valid() throws ValidationException
	{
		final MessageBuilder mb = new MessageBuilder();

		if (!mb.isEmpty())
		{
			throw new ValidationException(mb.getMessages());
		}
	}
}