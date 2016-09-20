package com.reelbook.model;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.reelbook.server.model.BaseSimpleModel;

@Entity
@Table(name = "adonis_erp_bank")
//@Audited
@Cacheable(value = true)
@SuppressWarnings("serial")
public class Bank extends BaseSimpleModel
{
	@Id
	@SequenceGenerator(name = "id", sequenceName = "adonis_erp_bank_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "id")
	private Long bankID;

	@Column(length = 50, unique = true)
	private String description;

	/**
	 */
	public Bank(String description)
	{
		this.bankID = 0l;
		this.description = description;
	}

	/**
	 */
	public Bank()
	{
		this("");
	}

	/**
	 */
	@Override
	public Long getID()
	{
		return bankID;
	}

	/**
	 */
	@Override
	public void setID(Long id)
	{
		this.bankID = id;
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
}