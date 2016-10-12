package com.reelbook.model;

import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.envers.Audited;
import com.reelbook.core.exception.ValidationException;
import com.reelbook.core.msg.MessageBuilder;
import com.reelbook.core.util.CompareUtil;

@Entity
@Table(name = "osiris_tax_portaluser")
@Cacheable(value = true)
@Audited
@SuppressWarnings("serial")
public class PortalUser extends User
{
	@Column(length = 50)
	private String emailAddress;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "taxPayerID", unique = true)
	private TaxPayer taxPayer;

	@Basic
	private Boolean validated;

	/**
	 */
	public PortalUser(String userName, String userPassword, List<Profile> profiles, String emailAddress, TaxPayer taxPayer)
	{
		super(userName, userPassword, profiles);

		this.emailAddress = emailAddress;
		this.taxPayer = taxPayer;
		this.validated = Boolean.FALSE;
	}

	/**
	 */
	public PortalUser(List<Profile> profiles)
	{
		this("", "", profiles, "", null);
	}

	/**
	 */
	public PortalUser()
	{
		this("", "", null, "", null);
	}

	/**
	 */
	public String getEmailAddress()
	{
		return emailAddress;
	}

	/**
	 */
	public void setEmailAddress(String emailAddress)
	{
		this.emailAddress = emailAddress;
	}

	/**
	 */
	public TaxPayer getTaxPayer()
	{
		return taxPayer;
	}

	/**
	 */
	public void setTaxPayer(TaxPayer taxPayer)
	{
		this.taxPayer = taxPayer;
	}

	/**
	 */
	public Boolean getValidated()
	{
		return validated;
	}

	/**
	 */
	public void setValidated(Boolean validated)
	{
		this.validated = validated;
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

		if (CompareUtil.isEmpty(getEmailAddress()))
		{
//			mb.addMessage(DBSMsgHandler.getMsg(PortalUser.class, "emailAddressEmpty"));
		}

		if (!mb.isEmpty())
		{
			throw new ValidationException(mb.getMessages());
		}
	}
}