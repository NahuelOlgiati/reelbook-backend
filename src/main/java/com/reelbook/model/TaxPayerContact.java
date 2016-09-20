package com.reelbook.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.reelbook.model.embeddable.Contact;
import com.reelbook.model.msc.TaxAgentContact;

@Entity
@Table(name = "osiris_tax_taxpayercontact")
@SuppressWarnings("serial")
public class TaxPayerContact extends TaxAgentContact
{
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "agentID", insertable = false, updatable = false)
	private TaxPayer taxPayer;

	/**
	 */
	public TaxPayerContact(Contact contact)
	{
		super(contact);
		this.taxPayer = null;
	}

	/**
	 */
	public TaxPayerContact()
	{
		this(null);
	}

	/**
	 */
	public TaxPayer getTaxPayer()
	{
		return taxPayer;
	}
}