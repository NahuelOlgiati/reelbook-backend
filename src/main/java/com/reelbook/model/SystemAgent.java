package com.reelbook.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.reelbook.core.msg.MessageBuilder;
import com.reelbook.core.util.CompareUtil;
import com.reelbook.model.embeddable.Document;
import com.reelbook.model.msc.Agent;
import com.reelbook.server.exception.ValidationException;

@Entity
@Table(name = "adonis_admin_systemagent")
//@Audited
@SuppressWarnings("serial")
public class SystemAgent extends Agent
{
	@Column(length = 50)
	private String firstName;

	@Column(length = 50)
	private String lastName;

	/**
	 */
	public SystemAgent(Document document, String firstName, String lastName)
	{
		super(document);

		this.firstName = firstName;
		this.lastName = lastName;
	}

	/**
	 */
	public SystemAgent()
	{
		this(null, "", "");
	}

	/**
	 */
	public String getFirstName()
	{
		return firstName;
	}

	/**
	 */
	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	/**
	 */
	public String getLastName()
	{
		return lastName;
	}

	/**
	 */
	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	/**
	 */
	@Override
	public void valid() throws ValidationException
	{
		final MessageBuilder mb = new MessageBuilder();

		try
		{
			super.valid();
		}
		catch (ValidationException v)
		{
			mb.addMessage(v.getMessages());
		}

		if (CompareUtil.isEmpty(getFirstName()))
		{
//			mb.addMessage(DBSMsgHandler.getMsg(SystemAgent.class, "firstNameEmpty"));
		}

		if (CompareUtil.isEmpty(getLastName()))
		{
//			mb.addMessage(DBSMsgHandler.getMsg(SystemAgent.class, "lastNameEmpty"));
		}

		if (!mb.isEmpty())
		{
			throw new ValidationException(mb.getMessages());
		}
	}
}