package com.reelbook.model;

import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;

import com.reelbook.core.msg.MessageBuilder;
import com.reelbook.core.util.CompareUtil;
import com.reelbook.server.exception.ValidationException;
import com.reelbook.server.model.BaseModel;

@MappedSuperclass
@SuppressWarnings("serial")
public abstract class TaxAgentBank extends BaseModel
{
	@Id
	@SequenceGenerator(name = "id", sequenceName = "adonis_erp_taxagentbank_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "id")
	private Long taxAgentBankID;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "bankID")
	private Bank bank;

	/**
	 */
	protected TaxAgentBank(Bank bank)
	{
		this.taxAgentBankID = 0l;
		this.bank = bank;
	}

	/**
	 */
	@Override
	public Long getID()
	{
		return taxAgentBankID;
	}

	/**
	 */
	@Override
	public void setID(Long id)
	{
		this.taxAgentBankID = id;
	}

	/**
	 */
	public Bank getBank()
	{
		return bank;
	}

	/**
	 */
	public void setBank(Bank bank)
	{
		this.bank = bank;
	}

	/**
	 */
	@Override
	public void valid() throws ValidationException
	{
		final MessageBuilder mb = new MessageBuilder();

		if (CompareUtil.isEmpty(getBank()))
		{
//			mb.addMessage(DBSMsgHandler.getMsg(TaxAgentBank.class, "bankEmpty"));
		}

		if (!mb.isEmpty())
		{
			throw new ValidationException(mb.getMessages());
		}
	}

	/**
	 */
	@Override
	public String getFullDescription()
	{
		final StringBuilder sb = new StringBuilder();

		if (!CompareUtil.isEmpty(getBank()))
		{
			sb.append(getBank().getDescription().trim());
		}

		return sb.toString();
	}
}