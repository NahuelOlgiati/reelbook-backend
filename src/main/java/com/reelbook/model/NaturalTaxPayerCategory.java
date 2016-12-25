package com.reelbook.model;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import org.hibernate.envers.Audited;
import com.reelbook.model.msc.TaxAgentClassification;

@Entity
@Table(name = "naturaltaxpayercategory")
@Audited
@Cacheable(value = true)
@XmlAccessorType(XmlAccessType.FIELD)
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