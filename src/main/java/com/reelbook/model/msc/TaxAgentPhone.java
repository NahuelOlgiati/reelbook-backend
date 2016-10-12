package com.reelbook.model.msc;

import javax.persistence.Embedded;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
import com.reelbook.core.exception.ValidationException;
import com.reelbook.core.model.BaseModel;
import com.reelbook.model.embeddable.Phone;

@MappedSuperclass
@SuppressWarnings("serial")
public abstract class TaxAgentPhone extends BaseModel
{
	@Id
	@SequenceGenerator(name = "id", sequenceName = "adonis_erp_taxagentphone_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "id")
	private Long taxAgentPhoneID;

	@Embedded
	private Phone phone;

	/**
	 */
	protected TaxAgentPhone(Phone phone)
	{
		this.taxAgentPhoneID = 0l;
		this.phone = phone;
	}

	/**
	 */
	@Override
	public Long getID()
	{
		return taxAgentPhoneID;
	}

	/**
	 */
	@Override
	public void setID(Long id)
	{
		this.taxAgentPhoneID = id;
	}

	/**
	 */
	public Phone getPhone()
	{
		if (phone == null)
		{
			phone = new Phone();
		}
		return phone;
	}

	/**
	 */
	@Override
	public void valid() throws ValidationException
	{
		getPhone().valid();
	}

	/**
	 */
	@Override
	public String getFullDescription()
	{
		return getPhone().getFullDescription();
	}

	/**
	 */
	@Override
	public int hashCode()
	{
		return getPhone().hashCode();
	}

	/**
	 */
	@Override
	public boolean equals(Object to)
	{
		return getPhone().equals(((TaxAgentPhone) to).getPhone());
	}
}