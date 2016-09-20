package com.reelbook.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.reelbook.model.msc.TaxAgentBank;

@Entity
@Table(name = "osiris_tax_taxpayerbank")
//@Audited
@SuppressWarnings("serial")
public class TaxPayerBank extends TaxAgentBank
{
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "agentID", insertable = false, updatable = false)
	private TaxPayer taxPayer;

	/**
	 */
	public TaxPayerBank(Bank bank)
	{
		super(bank);
		this.taxPayer = null;
	}

	/**
	 */
	public TaxPayerBank()
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