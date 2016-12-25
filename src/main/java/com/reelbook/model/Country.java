package com.reelbook.model;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import org.hibernate.envers.Audited;
import com.reelbook.core.model.BaseSummarySimpleModel;
import com.reelbook.core.util.CompareUtil;

@Entity
@Table(name = "country")
@Audited
@Cacheable(value = true)
@XmlAccessorType(XmlAccessType.FIELD)
@SuppressWarnings("serial")
public class Country extends BaseSummarySimpleModel
{
	@Id
	@SequenceGenerator(name = "id", sequenceName = "country_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "id")
	private Long countryID;

	@Column(length = 50, unique = true)
	private String description;

	@Column(length = 10, unique = true)
	private String summaryDescription;

	public Country(String description, String summaryDescription)
	{
		this.countryID = 0l;
		this.description = description;
		this.summaryDescription = summaryDescription;
	}

	public Country()
	{
		this("", "");
	}

	@Override
	public Long getID()
	{
		return countryID;
	}

	@Override
	public void setID(Long id)
	{
		this.countryID = id;
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
	public String getFullDescription()
	{
		final StringBuilder sb = new StringBuilder();

		if (!CompareUtil.isEmpty(getDescription()))
		{
			sb.append(getDescription().trim());
		}
		return sb.toString();
	}
}