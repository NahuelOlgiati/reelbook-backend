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

import com.reelbook.core.msg.MessageBuilder;
import com.reelbook.core.util.CompareUtil;
import com.reelbook.server.exception.ValidationException;
import com.reelbook.server.model.BaseSummarySimpleModel;

@Entity
@Table(name = "adonis_config_state")
//@Audited
@Cacheable(value = true)
@SuppressWarnings("serial")
public class State extends BaseSummarySimpleModel
{
	@Id
	@SequenceGenerator(name = "id", sequenceName = "adonis_config_state_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "id")
	private Long stateID;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "countryID")
	private Country country;

	@Column(length = 50, unique = true)
	private String description;

	@Column(length = 10, unique = true)
	private String summaryDescription;

	/**
	 */
	public State(Country country, String description, String summaryDescription)
	{
		this.stateID = 0l;
		this.country = country;
		this.description = description;
		this.summaryDescription = summaryDescription;
	}

	/**
	 */
	public State()
	{
		this(null, "", "");
	}

	/**
	 */
	@Override
	public Long getID()
	{
		return stateID;
	}

	/**
	 */
	@Override
	public void setID(Long id)
	{
		this.stateID = id;
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
//			mb.addMessage(DBSMsgHandler.getMsg(getClass(), "countryEmpty"));
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

		if (!CompareUtil.isEmpty(getDescription()))
		{
			sb.append(getDescription().trim());
		}
		/*
		 * if (!CompareUtil.isEmpty(getCountry())) { if (!CompareUtil.isEmpty(sb.toString())) { sb.append(", "); }
		 * sb.append(getCountry().getDescription().trim()); }
		 */
		return sb.toString();
	}
}