package com.reelbook.model.msc;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
import org.hibernate.envers.Audited;
import com.reelbook.core.model.BaseSummarySimpleModel;

@MappedSuperclass
@Audited
@SuppressWarnings("serial")
public abstract class TaxAgentClassification extends BaseSummarySimpleModel
{
	@Id
	@SequenceGenerator(name = "id", sequenceName = "taxagentclassification_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "id")
	private Long taxAgentClassificationID;

	@Column(length = 50, unique = true)
	private String description;

	@Column(length = 10, unique = true)
	private String summaryDescription;

	protected TaxAgentClassification(String description, String summaryDescription)
	{
		this.taxAgentClassificationID = 0l;
		this.description = description;
		this.summaryDescription = summaryDescription;
	}

	@Override
	public Long getID()
	{
		return taxAgentClassificationID;
	}

	@Override
	public void setID(Long id)
	{
		this.taxAgentClassificationID = id;
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

	@Override
	public String getSummaryDescription()
	{
		return summaryDescription;
	}

	@Override
	public void setSummaryDescription(String summaryDescription)
	{
		this.summaryDescription = summaryDescription;
	}
}