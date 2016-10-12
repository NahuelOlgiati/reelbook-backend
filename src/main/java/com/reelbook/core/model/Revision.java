package com.reelbook.core.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

@Entity
@Table(name = "adonis_common_revision")
@RevisionEntity
@SuppressWarnings("serial")
public final class Revision extends BaseRevisionModel
{
	@Id
	@SequenceGenerator(name = "id", sequenceName = "adonis_common_revision_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "id")
	@RevisionNumber
	private Long revisionID;

	@Temporal(TemporalType.TIMESTAMP)
	@RevisionTimestamp
	private Date date;

	@Column(length = 100)
	private String userName;

	/**
	 */
	public Revision(Date date, String userName)
	{
		this.revisionID = 0l;
		this.date = date;
		this.userName = userName;
	}

	/**
	 */
	public Revision()
	{
		this(null, "");
	}

	/**
	 */
	@Override
	public final Long getID()
	{
		return revisionID;
	}

	/**
	 */
	@Override
	public final void setID(Long id)
	{
		this.revisionID = id;
	}

	/**
	 */
	@Override
	public final Date getDate()
	{
		return date;
	}

	/**
	 */
	@Override
	public final void setDate(Date date)
	{
		this.date = date;
	}

	/**
	 */
	@Override
	public final String getUserName()
	{
		return userName;
	}

	/**
	 */
	@Override
	public final void setUserName(String userName)
	{
		this.userName = userName;
	}
}