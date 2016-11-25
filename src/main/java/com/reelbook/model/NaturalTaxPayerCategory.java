package com.reelbook.model;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.hibernate.envers.Audited;
import com.reelbook.model.msc.TaxAgentClassification;

@Entity
@Table(name = "osiris_tax_naturaltaxpayercategory")
@Audited
@Cacheable(value = true)
@SuppressWarnings("serial")
public class NaturalTaxPayerCategory extends TaxAgentClassification
{
	public NaturalTaxPayerCategory(String description, String summaryDescription)
	{
		super(description, summaryDescription);
	}

	public NaturalTaxPayerCategory()
	{
		this("", "");
	}
}