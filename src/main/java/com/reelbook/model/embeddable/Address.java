package com.reelbook.model.embeddable;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import com.reelbook.core.exception.ValidationException;
import com.reelbook.core.model.mpi.Describable;
import com.reelbook.core.model.mpi.Emptiable;
import com.reelbook.core.model.mpi.Validable;
import com.reelbook.core.msg.MessageBuilder;
import com.reelbook.core.util.CompareUtil;
import com.reelbook.model.Street;
import com.reelbook.model.Zone;

@Embeddable
@SuppressWarnings("serial")
public class Address implements Validable, Emptiable, Describable, Serializable
{
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "addStreetID")
	private Street addStreet;

	@Basic
	private Integer addNumber;

	@Column(length = 50)
	private String addFloor;

	@Column(length = 50)
	private String addApartment;

	@Column(length = 10)
	private String addZipCode;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "addStreet1ID")
	private Street addStreet1;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "addStreet2ID")
	private Street addStreet2;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "zoneID")
	private Zone zone;

	@Column(length = 100)
	private String residenceName;

	@Column(length = 500)
	private String addNotes;

	public Address(Street addStreet, Integer addNumber)
	{
		this.addStreet = addStreet;
		this.addNumber = addNumber;
		this.addFloor = "";
		this.addApartment = "";
		this.addZipCode = "";
		this.addStreet1 = null;
		this.addStreet2 = null;
		this.zone = null;
		this.residenceName = "";
		this.addNotes = "";
	}

	public Address()
	{
		this(null, 0);
	}

	public Street getAddStreet()
	{
		return addStreet;
	}

	public void setAddStreet(Street addStreet)
	{
		this.addStreet = addStreet;
		if (!CompareUtil.isEmpty(getZone()))
		{
			if (!addStreet.getCity().equals(getZone().getCity()))
			{
				setZone(null);
			}
		}
	}

	public Integer getAddNumber()
	{
		return addNumber;
	}

	public void setAddNumber(Integer addNumber)
	{
		this.addNumber = addNumber;
	}

	public String getAddFloor()
	{
		return addFloor;
	}

	public void setAddFloor(String addFloor)
	{
		this.addFloor = addFloor;
	}

	public String getAddApartment()
	{
		return addApartment;
	}

	public void setAddApartment(String addApartment)
	{
		this.addApartment = addApartment;
	}

	public String getAddZipCode()
	{
		return addZipCode;
	}

	public void setAddZipCode(String addZipCode)
	{
		this.addZipCode = addZipCode;
	}

	public Street getAddStreet1()
	{
		return addStreet1;
	}

	public void setAddStreet1(Street addStreet1)
	{
		this.addStreet1 = addStreet1;
	}

	public Street getAddStreet2()
	{
		return addStreet2;
	}

	public void setAddStreet2(Street addStreet2)
	{
		this.addStreet2 = addStreet2;
	}

	public Zone getZone()
	{
		return zone;
	}

	public void setZone(Zone zone)
	{
		this.zone = zone;
	}

	public String getResidenceName()
	{
		return residenceName;
	}

	public void setResidenceName(String residenceName)
	{
		this.residenceName = residenceName;
	}

	public String getAddNotes()
	{
		return addNotes;
	}

	public void setAddNotes(String addNotes)
	{
		this.addNotes = addNotes;
	}

	@Override
	public void valid() throws ValidationException
	{
		final MessageBuilder mb = new MessageBuilder();

		if (CompareUtil.isEmpty(getAddStreet()))
		{
			// mb.addMessage(DBSMsgHandler.getMsg(getClass(), "addStreetEmpty"));
		}

		if (CompareUtil.isNegative(getAddNumber()))
		{
			// mb.addMessage(DBSMsgHandler.getMsg(getClass(), "addNumberInvalid"));
		}

		if (!mb.isEmpty())
		{
			throw new ValidationException(mb.getMessages());
		}
	}

	@Override
	public boolean isBlank()
	{
		return CompareUtil.isEmpty(getAddStreet());
	}

	@Override
	public String getFullDescription()
	{
		final StringBuilder sb = new StringBuilder();

		if (!CompareUtil.isEmpty(getAddStreet()))
		{
			sb.append(getAddStreet().getDescription().trim());

			if (!CompareUtil.isEmpty(getAddNumber()))
			{
				sb.append(" ").append(getAddNumber());
			}

			if (!CompareUtil.isEmpty(getResidenceName()))
			{
				sb.append(", ").append(getResidenceName().trim());
			}

			if (!CompareUtil.isEmpty(getAddApartment()))
			{
				sb.append(", ").append(getAddFloor()).append(" ").append(getAddApartment().trim());
			}

			if (!CompareUtil.isEmpty(getZone()))
			{
				sb.append(", ").append(getZone().getDescription().trim());
			}
			sb.append(", ").append(getAddStreet().getCity().getFullDescription());
		}
		return sb.toString();
	}

	public String getDescription()
	{
		final StringBuilder sb = new StringBuilder();

		if (!CompareUtil.isEmpty(getAddStreet()))
		{
			sb.append(getAddStreet().getDescription().trim());

			if (!CompareUtil.isEmpty(getAddNumber()))
			{
				sb.append(" ").append(getAddNumber());
			}

			if (!CompareUtil.isEmpty(getAddApartment()))
			{
				sb.append(", ").append(getAddFloor()).append(" ").append(getAddApartment().trim());
			}

			if (!CompareUtil.isEmpty(getZone()))
			{
				sb.append(", ").append(getZone().getDescription().trim());
			}
			sb.append(", ").append(getAddStreet().getCity().getDescription());
		}
		return sb.toString();
	}

	public void copy(Address address)
	{
		this.addStreet = address.getAddStreet();
		this.addNumber = address.getAddNumber();
		this.addFloor = address.getAddFloor();
		this.addApartment = address.getAddApartment();
		this.addZipCode = address.getAddZipCode();
		this.addStreet1 = address.getAddStreet1();
		this.addStreet2 = address.getAddStreet2();
		this.zone = address.getZone();
		this.residenceName = address.getResidenceName();
		this.addNotes = address.getAddNotes();
	}

	@Override
	public int hashCode()
	{
		int hashCode = 0;

		if (getAddStreet() != null)
			hashCode = hashCode + getAddStreet().hashCode();
		if (getAddNumber() != null)
			hashCode = hashCode + getAddNumber().hashCode();
		if (getAddFloor() != null)
			hashCode = hashCode + getAddFloor().hashCode();
		if (getAddApartment() != null)
			hashCode = hashCode + getAddApartment().hashCode();
		if (getAddZipCode() != null)
			hashCode = hashCode + getAddZipCode().hashCode();
		if (getAddStreet1() != null)
			hashCode = hashCode + getAddStreet1().hashCode();
		if (getAddStreet2() != null)
			hashCode = hashCode + getAddStreet2().hashCode();
		if (getZone() != null)
			hashCode = hashCode + getZone().hashCode();
		if (getResidenceName() != null)
			hashCode = hashCode + getResidenceName().hashCode();
		if (getAddNotes() != null)
			hashCode = hashCode + getAddNotes().hashCode();

		return hashCode;
	}

	@Override
	public boolean equals(Object to)
	{
		final Address a = (Address) to;

		return equals(getAddStreet(), a.getAddStreet()) && equals(getAddNumber(), a.getAddNumber()) && equals(getAddFloor(), a.getAddFloor())
				&& equals(getAddApartment(), a.getAddApartment()) && equals(getAddZipCode(), a.getAddZipCode())
				&& equals(getAddStreet1(), a.getAddStreet1()) && equals(getAddStreet2(), a.getAddStreet2()) && equals(getZone(), a.getZone())
				&& equals(getResidenceName(), a.getResidenceName()) && equals(getAddNotes(), a.getAddNotes());
	}

	private final boolean equals(Object a1, Object a2)
	{
		if (a1 == null)
		{
			return (a2 == null);
		}
		return a1.equals(a2);
	}
}