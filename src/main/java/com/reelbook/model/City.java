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
import com.reelbook.core.exception.ValidationException;
import com.reelbook.core.model.BaseSimpleModel;
import com.reelbook.core.msg.MessageBuilder;
import com.reelbook.core.util.CompareUtil;

@Entity
@Table(name = "adonis_config_city", uniqueConstraints = @UniqueConstraint(columnNames = {"districtID", "description"}))
@Audited
@Cacheable(value = true)
@SuppressWarnings("serial")
public class City extends BaseSimpleModel
{
	@Id
	@SequenceGenerator(name = "id", sequenceName = "adonis_config_city_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "id")
	private Long cityID;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "districtID")
	private District district;

	@Column(length = 50)
	private String description;

	public City(District district, String description)
	{
		this.cityID = 0l;
		this.district = district;
		this.description = description;
	}

	public City()
	{
		this(null, "");
	}

	@Override
	public Long getID()
	{
		return cityID;
	}

	@Override
	public void setID(Long id)
	{
		this.cityID = id;
	}

	public District getDistrict()
	{
		return district;
	}

	public void setDistrict(District district)
	{
		this.district = district;
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

		if (CompareUtil.isEmpty(getDistrict()))
		{
			// mb.addMessage(DBSMsgHandler.getMsg(getClass(), "districtEmpty"));
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

		if (!CompareUtil.isEmpty(getDistrict()))
		{
			if (!CompareUtil.isEmpty(sb.toString()))
			{
				sb.append(", ");
			}
			sb.append(getDistrict().getFullDescription());
		}
		return sb.toString();
	}
}