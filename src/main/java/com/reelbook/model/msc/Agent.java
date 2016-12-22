package com.reelbook.model.msc;

import javax.persistence.Embedded;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
import org.hibernate.envers.Audited;
import com.reelbook.core.exception.ValidationException;
import com.reelbook.core.model.BaseModel;
import com.reelbook.model.embeddable.Document;

@MappedSuperclass
@Audited
@SuppressWarnings("serial")
public abstract class Agent extends BaseModel
{
	@Id
	@SequenceGenerator(name = "id", sequenceName = "agent_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "id")
	private Long agentID;

	@Embedded
	private Document document;

	protected Agent(Document document)
	{
		this.agentID = 0l;
		this.document = document;
	}

	@Override
	public Long getID()
	{
		return agentID;
	}

	@Override
	public void setID(Long id)
	{
		this.agentID = id;
	}

	public Document getDocument()
	{
		if (document == null)
		{
			document = new Document();
		}
		return document;
	}

	@Override
	public void valid() throws ValidationException
	{
		getDocument().valid();
	}
}