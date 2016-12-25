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

import com.reelbook.core.model.BaseSimpleModel;

@Entity
@Table(name = "maritalstatus")
@Cacheable(value = true)
@XmlAccessorType(XmlAccessType.FIELD)
@SuppressWarnings("serial")
public class MaritalStatus extends BaseSimpleModel
{
	@Id
	@SequenceGenerator(name = "id", sequenceName = "maritalstatus_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "id")
	private Long maritalStatusID;

	@Column(length = 50, unique = true)
	private String description;

	public MaritalStatus(String description)
	{
		this.maritalStatusID = 0l;
		this.description = description;
	}

	public MaritalStatus()
	{
		this("");
	}

	@Override
	public Long getID()
	{
		return maritalStatusID;
	}

	@Override
	public void setID(Long id)
	{
		this.maritalStatusID = id;
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
}