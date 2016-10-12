package com.reelbook.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import com.reelbook.core.exception.ValidationException;
import com.reelbook.core.msg.MessageBuilder;
import com.reelbook.core.util.CompareUtil;
import com.reelbook.model.embeddable.Document;
import com.reelbook.model.enumeration.GenderEnum;
import com.reelbook.model.enumeration.TaxPayerTypeEnum;

@Entity
@Table(name = "osiris_tax_naturaltaxpayer")
@Audited
@SuppressWarnings("serial")
public class NaturalTaxPayer extends TaxPayer
{
	@Column(length = 100)
	private String firstName;

	@Column(length = 100)
	private String lastName;

	@Column(length = 100)
	private String maternalLastName;

	@Enumerated(EnumType.STRING)
	@NotAudited
	private GenderEnum gender;

	@Temporal(TemporalType.DATE)
	@NotAudited
	private Date birthDate;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "maritalStatusID")
	@NotAudited
	private MaritalStatus maritalStatus;

	@Embedded
	private Document secondaryDocument;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "naturalTaxPayerCategoryID")
	private NaturalTaxPayerCategory naturalTaxPayerCategory;

	/**
	 */
	public NaturalTaxPayer(Document document)
	{
		super(document);
		this.firstName = "";
		this.lastName = "";
		this.maternalLastName = "";
		this.gender = GenderEnum.MALE;
		this.birthDate = null;
		this.maritalStatus = null;
		this.secondaryDocument = null;
		this.naturalTaxPayerCategory = null;
	}

	/**
	 */
	public NaturalTaxPayer()
	{
		this(null);
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
	public String getMaternalLastName()
	{
		return maternalLastName;
	}

	/**
	 */
	public void setMaternalLastName(String maternalLastName)
	{
		this.maternalLastName = maternalLastName;
	}

	/**
	 */
	public GenderEnum getGender()
	{
		return gender;
	}

	/**
	 */
	public void setGender(GenderEnum gender)
	{
		this.gender = gender;
	}

	/**
	 */
	public Date getBirthDate()
	{
		return birthDate;
	}

	/**
	 */
	public void setBirthDate(Date birthDate)
	{
		this.birthDate = birthDate;
	}

	/**
	 */
	public MaritalStatus getMaritalStatus()
	{
		return maritalStatus;
	}

	/**
	 */
	public void setMaritalStatus(MaritalStatus maritalStatus)
	{
		this.maritalStatus = maritalStatus;
	}

	/**
	 */
	public Document getSecondaryDocument()
	{
		if (secondaryDocument == null)
		{
			secondaryDocument = new Document();
		}
		return secondaryDocument;
	}

	/**
	 */
	public Document getSecondaryDocumentRO()
	{
		return secondaryDocument;
	}

	/**
	 */
	public void setSecondaryDocument(Document secondaryDocument)
	{
		this.secondaryDocument = secondaryDocument;
	}

	/**
	 */
	public NaturalTaxPayerCategory getNaturalTaxPayerCategory()
	{
		return naturalTaxPayerCategory;
	}

	/**
	 */
	public void setNaturalTaxPayerCategory(NaturalTaxPayerCategory naturalTaxPayerCategory)
	{
		this.naturalTaxPayerCategory = naturalTaxPayerCategory;
	}

	/**
	 */
	@Override
	public TaxPayerTypeEnum getTaxPayerType()
	{
		return TaxPayerTypeEnum.NATURAL;
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
			// mb.addMessage(DBSMsgHandler.getMsg(getClass(), "firstNameEmpty"));
		}

		if (CompareUtil.isEmpty(getLastName()))
		{
			// mb.addMessage(DBSMsgHandler.getMsg(getClass(), "lastNameEmpty"));
		}

		if (CompareUtil.isEmpty(getGender()))
		{
			// mb.addMessage(DBSMsgHandler.getMsg(getClass(), "genderEmpty"));
		}

		if (CompareUtil.isEmpty(getBirthDate()))
		{
			// mb.addMessage(DBSMsgHandler.getMsg(getClass(), "birthDateEmpty"));
		}

		if (CompareUtil.isEmpty(getMaritalStatus()))
		{
			// mb.addMessage(DBSMsgHandler.getMsg(getClass(), "maritalStatusEmpty"));
		}

		if (!CompareUtil.isEmpty(getSecondaryDocument()))
		{
			try
			{
				getSecondaryDocument().valid();
			}
			catch (ValidationException v)
			{
				mb.addMessage(v.getMessages());
			}
		}
		else
		{
			setSecondaryDocument(null);
		}

		if (CompareUtil.isEmpty(getNaturalTaxPayerCategory()))
		{
			// mb.addMessage(DBSMsgHandler.getMsg(getClass(), "naturalTaxPayerCategoryEmpty"));
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
		final StringBuilder sb = new StringBuilder(super.getFullDescription().trim());

		if (!CompareUtil.isEmpty(getLastName()))
		{
			if (!CompareUtil.isEmpty(sb.toString()))
			{
				sb.append(":");
			}
			sb.append(getLastName().trim());
		}

		if (!CompareUtil.isEmpty(getFirstName()))
		{
			if (!CompareUtil.isEmpty(sb.toString()))
			{
				sb.append(" ");
			}
			sb.append(getFirstName().trim());
		}

		if (!CompareUtil.isEmpty(getDocument()))
		{
			if (!CompareUtil.isEmpty(sb.toString()))
			{
				sb.append(" ");
			}
			sb.append("(" + getDocument().getDocumentType().getSummaryDescription().trim() + " " + getDocument().getDocumentNumber() + ")");
		}
		return sb.toString();
	}

	/**
	 */
	@Override
	public String getFullName()
	{
		final StringBuilder sb = new StringBuilder();

		if (!CompareUtil.isEmpty(getLastName()))
		{
			sb.append(getLastName().trim());
		}

		if (!CompareUtil.isEmpty(getFirstName()))
		{
			if (!CompareUtil.isEmpty(sb.toString()))
			{
				sb.append(" ");
			}
			sb.append(getFirstName().trim());
		}
		return sb.toString();
	}
}