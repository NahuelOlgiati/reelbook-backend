package com.reelbook.model.msc;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import com.reelbook.core.exception.ValidationException;
import com.reelbook.core.msg.MessageBuilder;
import com.reelbook.core.util.CompareUtil;
import com.reelbook.model.Country;
import com.reelbook.model.embeddable.Address;
import com.reelbook.model.embeddable.Document;

@MappedSuperclass
@Audited
@SuppressWarnings("serial")
public abstract class TaxAgent<P extends TaxAgentPhone, C extends TaxAgentContact> extends Agent
{
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "countryID")
	@NotAudited
	private Country country;

	@Embedded
	private Address legalAddress;

	@Column(length = 500)
	private String notes;

	protected TaxAgent(Document document)
	{
		super(document);
		this.country = null;
		this.legalAddress = null;
		this.notes = "";
	}

	public Country getCountry()
	{
		return country;
	}

	public void setCountry(Country country)
	{
		this.country = country;
	}

	public Address getLegalAddress()
	{
		if (legalAddress == null)
		{
			legalAddress = new Address();
		}
		return legalAddress;
	}

	public String getNotes()
	{
		return notes;
	}

	public void setNotes(String notes)
	{
		this.notes = notes;
	}

	public abstract List<P> getPhoneList();

	public abstract P getPhoneDefault();

	public abstract void setPhoneDefault(P phoneDefault);

	public abstract List<C> getContactList();

	public abstract C getContactDefault();

	public abstract void setContactDefault(C contactDefault);

	@Override
	public void initLazyElements()
	{
		super.initLazyElements();
		getPhoneList().size();
		getContactList().size();
	}

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

		if (CompareUtil.isEmpty(getCountry()))
		{
			// mb.addMessage(DBSMsgHandler.getMsg(TaxAgent.class,
			// "countryEmpty"));
		}

		try
		{
			getLegalAddress().valid();
		}
		catch (ValidationException v)
		{
			mb.addMessage(v.getMessages());
		}

		if (!CompareUtil.isEmpty(getPhoneList()))
		{
			try
			{
				for (final TaxAgentPhone tap : getPhoneList())
				{
					tap.valid();
				}
			}
			catch (ValidationException v)
			{
				mb.addMessage(v.getMessages());
			}

			if (!getPhoneList().contains(getPhoneDefault()))
			{
				// mb.addMessage(DBSMsgHandler.getMsg(TaxAgent.class,
				// "phoneDefaultEmpty"));
			}
		}
		else
		{
			setPhoneDefault(null);
		}

		if (!CompareUtil.isEmpty(getContactList()))
		{
			try
			{
				for (final TaxAgentContact tac : getContactList())
				{
					tac.valid();
				}
			}
			catch (ValidationException v)
			{
				mb.addMessage(v.getMessages());
			}

			if (!getContactList().contains(getContactDefault()))
			{
				// mb.addMessage(DBSMsgHandler.getMsg(TaxAgent.class,
				// "contactDefaultEmpty"));
			}
		}
		else
		{
			setContactDefault(null);
		}

		if (!mb.isEmpty())
		{
			throw new ValidationException(mb.getMessages());
		}
	}
}