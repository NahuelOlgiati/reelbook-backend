package com.reelbook.model.embeddable;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import com.reelbook.core.exception.ValidationException;
import com.reelbook.core.model.mpi.Emptiable;
import com.reelbook.core.model.mpi.Validable;
import com.reelbook.core.msg.MessageBuilder;
import com.reelbook.core.util.CompareUtil;
import com.reelbook.model.DocumentType;

@Embeddable
@SuppressWarnings("serial")
public class Document implements Validable, Emptiable, Serializable
{
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "documentTypeID")
	private DocumentType documentType;

	@Column(length = 20)
	private String documentNumber;

	public Document(DocumentType documentType, String documentNumber)
	{
		this.documentType = documentType;
		this.documentNumber = documentNumber;
	}

	public Document()
	{
		this(null, "");
	}

	// No Borrar anotations, workaround para la generacion del metamodel.
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "documentTypeID")
	public DocumentType getDocumentType()
	{
		if (documentType == null)
		{
			documentType = new DocumentType();
		}
		return documentType;
	}

	public void setDocumentType(DocumentType documentType)
	{
		this.documentType = documentType;
	}

	public String getDocumentNumber()
	{
		return documentNumber;
	}

	public void setDocumentNumber(String documentNumber)
	{
		this.documentNumber = documentNumber;
	}

	@Override
	public void valid() throws ValidationException
	{
		final MessageBuilder mb = new MessageBuilder();

		if (CompareUtil.isEmpty(getDocumentType()))
		{
			// mb.addMessage(DBSMsgHandler.getMsg(getClass(), "documentTypeEmpty"));
		}

		if (CompareUtil.isEmpty(getDocumentNumber()))
		{
			// mb.addMessage(DBSMsgHandler.getMsg(getClass(), "documentNumberEmpty"));
		}

		if (!mb.isEmpty())
		{
			throw new ValidationException(mb.getMessages());
		}
	}

	@Override
	public boolean isBlank()
	{
		return CompareUtil.isEmpty(getDocumentType()) && CompareUtil.isEmpty(getDocumentNumber());
	}

	@Override
	public int hashCode()
	{
		return getDocumentType().hashCode() + getDocumentNumber().hashCode();
	}

	@Override
	public boolean equals(Object to)
	{
		final Document d = (Document) to;
		return getDocumentNumber().equals(d.getDocumentNumber()) && getDocumentType().equals(d.getDocumentType());
	}

	public String getTypeNumberDocument()
	{
		return this.getDocumentType().getSummaryDescription() + " " + this.getDocumentNumber();
	}
}