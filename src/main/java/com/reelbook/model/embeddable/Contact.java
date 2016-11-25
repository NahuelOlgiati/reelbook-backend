package com.reelbook.model.embeddable;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import com.reelbook.core.exception.ValidationException;
import com.reelbook.core.model.mpi.Describable;
import com.reelbook.core.model.mpi.Validable;
import com.reelbook.core.msg.MessageBuilder;
import com.reelbook.core.util.CompareUtil;
import com.reelbook.model.enumeration.ContactTypeEnum;

@Embeddable
@SuppressWarnings("serial")
public class Contact implements Validable, Describable, Serializable
{
	@Enumerated(EnumType.STRING)
	private ContactTypeEnum contactType;

	@Column(length = 100)
	private String contact;

	@Column(length = 255)
	private String notes;

	public Contact(ContactTypeEnum contactType, String contact, String notes)
	{
		this.contactType = contactType;
		this.contact = contact;
		this.notes = notes;
	}

	public Contact()
	{
		this(ContactTypeEnum.MAIL, "", "");
	}

	public ContactTypeEnum getContactType()
	{
		return contactType;
	}

	public void setContactType(ContactTypeEnum contactType)
	{
		this.contactType = contactType;
	}

	public String getContact()
	{
		return contact;
	}

	public void setContact(String contact)
	{
		this.contact = contact;
	}

	public String getNotes()
	{
		return notes;
	}

	public void setNotes(String notes)
	{
		this.notes = notes;
	}

	@Override
	public void valid() throws ValidationException
	{
		final MessageBuilder mb = new MessageBuilder();

		if (CompareUtil.isEmpty(getContactType()))
		{
			// mb.addMessage(DBSMsgHandler.getMsg(getClass(), "contactTypeEmpty"));
		}

		if (CompareUtil.isEmpty(getContact()))
		{
			// mb.addMessage(DBSMsgHandler.getMsg(getClass(), "contactEmpty"));
		}

		if (!mb.isEmpty())
		{
			throw new ValidationException(mb.getMessages());
		}
	}

	@Override
	public String getFullDescription()
	{
		final StringBuilder sb = new StringBuilder();

		if (!CompareUtil.isEmpty(getContact()))
		{
			if (!CompareUtil.isEmpty(getContactType()))
			{
				sb.append(getContactType().getLabel().trim());
				sb.append(": ");
			}
			sb.append(getContact().trim());
		}
		return sb.toString();
	}

	@Override
	public int hashCode()
	{
		return getContactType().hashCode() + getContact().hashCode();
	}

	@Override
	public boolean equals(Object to)
	{
		final Contact c = (Contact) to;
		return getContactType().equals(c.getContactType()) && getContact().equals(c.getContact());
	}
}