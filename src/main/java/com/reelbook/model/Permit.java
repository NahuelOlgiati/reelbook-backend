package com.reelbook.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.hibernate.envers.Audited;
import com.reelbook.core.model.BaseSimpleModel;

@Entity
@Table(name = "adonis_admin_permit")
@Audited
@SuppressWarnings("serial")
public class Permit extends BaseSimpleModel
{
	@Id
	@SequenceGenerator(name = "id", sequenceName = "adonis_admin_permit_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "id")
	private Long permitID;

	@Column(length = 100)
	private String module;

	@Column(length = 100, unique = true)
	private String code;

	@Column(length = 300)
	private String description;

	/**
	 */
	public Permit(String module, String code, String description)
	{
		this.permitID = 0l;
		this.module = module;
		this.code = code;
		this.description = description;
	}

	/**
	 */
	public Permit()
	{
		this("", "", "");
	}

	/**
	 */
	@Override
	public Long getID()
	{
		return permitID;
	}

	/**
	 */
	@Override
	public void setID(Long id)
	{
		this.permitID = id;
	}

	/**
	 */
	public String getModule()
	{
		return module;
	}

	/**
	 */
	public void setModule(String module)
	{
		this.module = module;
	}

	/**
	 */
	public String getCode()
	{
		return code;
	}

	/**
	 */
	public void setCode(String code)
	{
		this.code = code;
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
	public String getFullDescription()
	{
		return getModule() + " - " + getDescription();
	}
}