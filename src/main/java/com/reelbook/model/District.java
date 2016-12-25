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
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import org.hibernate.envers.Audited;
import com.reelbook.core.exception.ValidationException;
import com.reelbook.core.model.BaseSummarySimpleModel;
import com.reelbook.core.msg.MessageBuilder;
import com.reelbook.core.util.CompareUtil;

@Entity
@Table(name = "district")
@Audited
@Cacheable(value = true)
@XmlAccessorType(XmlAccessType.FIELD)
@SuppressWarnings("serial")
public class District extends BaseSummarySimpleModel
{
	@Id
	@SequenceGenerator(name = "id", sequenceName = "district_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "id")
	private Long districtID;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "stateID")
	private State state;

	@Column(length = 50, unique = true)
	private String description;

	@Column(length = 10, unique = true)
	private String summaryDescription;

	public District(State state, String description, String summaryDescription)
	{
		this.districtID = 0l;
		this.state = state;
		this.description = description;
		this.summaryDescription = summaryDescription;
	}

	public District()
	{
		this(null, "", "");
	}

	@Override
	public Long getID()
	{
		return districtID;
	}

	@Override
	public void setID(Long id)
	{
		this.districtID = id;
	}

	public State getState()
	{
		return state;
	}

	public void setState(State state)
	{
		this.state = state;
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
	public String getSummaryDescription()
	{
		return summaryDescription;
	}

	@Override
	public void setSummaryDescription(String summaryDescription)
	{
		this.summaryDescription = summaryDescription;
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

		if (CompareUtil.isEmpty(getState()))
		{
			// mb.addMessage(DBSMsgHandler.getMsg(getClass(), "stateEmpty"));
		}

		if (!mb.isEmpty())
		{
			throw new ValidationException(mb.getMessages());
		}
	}

	@Override
	public String getFullDescription()
	{
		final StringBuilder sb = new StringBuilder();

		if (!CompareUtil.isEmpty(getDescription()))
		{
			sb.append(getDescription().trim());
		}

		if (!CompareUtil.isEmpty(getState()))
		{
			if (!CompareUtil.isEmpty(sb.toString()))
			{
				sb.append(", ");
			}
			sb.append(getState().getFullDescription());
		}
		return sb.toString();
	}
}