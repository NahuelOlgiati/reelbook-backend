package com.reelbook.model.msc;

import javax.persistence.Embedded;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
import com.reelbook.core.exception.ValidationException;
import com.reelbook.core.model.BaseModel;
import com.reelbook.model.embeddable.Contact;

@MappedSuperclass
@SuppressWarnings("serial")
public abstract class TaxAgentContact extends BaseModel
{
	@Id
	@SequenceGenerator(name = "id", sequenceName = "adonis_erp_taxagentcontact_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "id")
	private Long taxAgentContactID;

	@Embedded
	private Contact contact;

	/**
	 */
	protected TaxAgentContact(Contact contact)
	{
		this.taxAgentContactID = 0l;
		this.contact = contact;
	}

	/**
	 */
	@Override
	public Long getID()
	{
		return taxAgentContactID;
	}

	/**
	 */
	@Override
	public void setID(Long id)
	{
		this.taxAgentContactID = id;
	}

	/**
	 */
	public Contact getContact()
	{
		if (contact == null)
		{
			contact = new Contact();
		}
		return contact;
	}

	/**
	 */
	@Override
	public void valid() throws ValidationException
	{
		getContact().valid();
	}

	/**
	 */
	@Override
	public String getFullDescription()
	{
		return getContact().getFullDescription();
	}

	/**
	 */
	@Override
	public int hashCode()
	{
		return getContact().hashCode();
	}

	/**
	 */
	@Override
	public boolean equals(Object to)
	{
		return getContact().equals(((TaxAgentContact) to).getContact());
	}
}