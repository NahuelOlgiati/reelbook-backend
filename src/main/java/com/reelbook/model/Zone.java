package com.reelbook.model;

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
import org.hibernate.envers.Audited;
import com.reelbook.core.exception.ValidationException;
import com.reelbook.core.model.BaseSimpleModel;
import com.reelbook.core.msg.MessageBuilder;
import com.reelbook.core.util.CompareUtil;

@Entity
@Table(name = "adonis_config_zone")
@Audited
@SuppressWarnings("serial")
public class Zone extends BaseSimpleModel
{
	@Id
	@SequenceGenerator(name = "id", sequenceName = "adonis_config_zone_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "id")
	private Long zoneID;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "cityID")
	private City city;

	@Column(length = 50)
	private String description;

	public Zone(City city, String description)
	{
		this.zoneID = 0l;
		this.city = city;
		this.description = description;
	}

	public Zone()
	{
		this(null, "");
	}

	@Override
	public Long getID()
	{
		return zoneID;
	}

	@Override
	public void setID(Long id)
	{
		this.zoneID = id;
	}

	public City getCity()
	{
		return city;
	}

	public void setCity(City city)
	{
		this.city = city;
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

		if (CompareUtil.isEmpty(getCity()))
		{
			// mb.addMessage(DBSMsgHandler.getMsg(getClass(), "cityEmpty"));
		}

		if (!mb.isEmpty())
		{
			throw new ValidationException(mb.getMessages());
		}
	}
}