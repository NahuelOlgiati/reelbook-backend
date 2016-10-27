package com.reelbook.model;

import javax.persistence.Cacheable;
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
import javax.persistence.UniqueConstraint;
import org.hibernate.envers.Audited;
import com.google.gson.annotations.SerializedName;
import com.reelbook.core.exception.ValidationException;
import com.reelbook.core.model.BaseSummarySimpleModel;
import com.reelbook.core.msg.MessageBuilder;
import com.reelbook.core.util.CharUtil;
import com.reelbook.core.util.CompareUtil;

@Entity
@Table(
		name = "adonis_config_artist",
		uniqueConstraints = {
				@UniqueConstraint(columnNames = {"countryID", "description"}),
				@UniqueConstraint(columnNames = {"countryID", "summaryDescription"})})
@Audited
@Cacheable(value = true)
@SuppressWarnings("serial")
public class Artist extends BaseSummarySimpleModel
{
	@Id
	@SerializedName(value = "id")
	@SequenceGenerator(name = "id", sequenceName = "adonis_config_artist_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "id")
	private Long artistID;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "countryID")
	private Country country;

	@Column(length = 50)
	private String description;

	@Column(length = 10)
	private String summaryDescription;

	@Column(length = 50)	
	private String format;

	/**
	 */
	public Artist(Country country, String description, String summaryDescription)
	{
		this.artistID = 0l;
		this.country = country;
		this.description = description;
		this.summaryDescription = summaryDescription;
		this.format = "";
	}

	/**
	 */
	public Artist()
	{
		this(null, "", "");
	}

	/**
	 */
	@Override
	public Long getID()
	{
		return artistID;
	}

	/**
	 */
	@Override
	public void setID(Long id)
	{
		this.artistID = id;
	}

	/**
	 */
	public Country getCountry()
	{
		return country;
	}

	/**
	 */
	public void setCountry(Country country)
	{
		this.country = country;
	}

	/**
	 */
	@Override
	public String getDescription()
	{
		return description;
	}

	/**
	 */
	@Override
	public void setDescription(String description)
	{
		this.description = description;
	}

	/**
	 */
	@Override
	public String getSummaryDescription()
	{
		return summaryDescription;
	}

	/**
	 */
	@Override
	public void setSummaryDescription(String summaryDescription)
	{
		this.summaryDescription = summaryDescription;
	}

	/**
	 */
	public String getFormat()
	{
		return format;
	}

	/**
	 */
	public void setFormat(String format)
	{
		this.format = format;
	}

	/**
	 */
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

	/**
	 */
	@Override
	public String getFullDescription()
	{
		final StringBuilder sb = new StringBuilder();

		if (!CompareUtil.isEmpty(getSummaryDescription()))
		{
			if (!CompareUtil.isEmpty(getCountry()))
			{
				sb.append(getCountry().getSummaryDescription().trim());
				sb.append(CharUtil.getPipeSeparator());
			}
			sb.append(getSummaryDescription());
		}
		return sb.toString();
	}
}