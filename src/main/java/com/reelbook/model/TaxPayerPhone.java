package com.reelbook.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.reelbook.model.embeddable.Phone;
import com.reelbook.model.msc.TaxAgentPhone;

@Entity
@Table(name = "taxpayerphone")
@XmlAccessorType(XmlAccessType.FIELD)
@SuppressWarnings("serial")
public class TaxPayerPhone extends TaxAgentPhone
{
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "agentID", insertable = false, updatable = false)
	private TaxPayer taxPayer;

	public TaxPayerPhone(Phone phone)
	{
		super(phone);
		this.taxPayer = null;
	}

	public TaxPayerPhone()
	{
		this(null);
	}

	public TaxPayer getTaxPayer()
	{
		return taxPayer;
	}
}