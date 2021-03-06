package com.reelbook.model;

import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
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
import com.reelbook.core.model.BaseSimpleModel;
import com.reelbook.core.msg.MessageBuilder;
import com.reelbook.core.util.CompareUtil;

@Entity
@Table(name = "artist")
@Audited
@Cacheable(value = true)
@XmlAccessorType(XmlAccessType.FIELD)
@SuppressWarnings("serial")
public class Artist extends BaseSimpleModel
{
	@Id
	@SequenceGenerator(name = "id", sequenceName = "artist_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "id")
	private Long artistID;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "countryID")
	private Country country;

	@Column(length = 50)
	private String description;
	
	@Basic
	private Long userID;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "fileID")
	private File file;

	public Artist(Country country, String description)
	{
		this.artistID = 0l;
		this.country = country;
		this.description = description;
		this.file = null;
	}

	public Artist()
	{
		this(null, "");
	}

	@Override
	public Long getID()
	{
		return artistID;
	}

	@Override
	public void setID(Long id)
	{
		this.artistID = id;
	}

	public Country getCountry()
	{
		return country;
	}

	public void setCountry(Country country)
	{
		this.country = country;
	}

	public File getFile()
	{
		return file;
	}

	public void setFile(File file)
	{
		this.file = file;
	}
	
	public Long getUserID() 
	{
		return userID;
	}

	public void setUserID(Long id) 
	{
		this.userID = id;
	}

	@Override
	public String getDescription()
	{
		return description;
	}

	@Override
	public void setDescription(String description)
	{
		this.description = description;
	}

	@Override
	public void valid() throws ValidationException
	{
		final MessageBuilder mb = new MessageBuilder();

		try
		{
			super.valid();
		}
		catch (ValidationException v)
		{
			mb.addMessage(v.getMessages());
		}

		if (CompareUtil.isEmpty(getCountry()))
		{
			// mb.addMessage(DBSMsgHandler.getMsg(getClass(), "countryEmpty"));
		}

		if (!mb.isEmpty())
		{
			throw new ValidationException(mb.getMessages());
		}
	}
}