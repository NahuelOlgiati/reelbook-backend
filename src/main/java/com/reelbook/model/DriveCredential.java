package com.reelbook.model;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import org.hibernate.envers.Audited;
import com.reelbook.core.exception.ValidationException;
import com.reelbook.core.model.BaseModel;
import com.reelbook.core.msg.MessageBuilder;
import com.reelbook.rest.annotation.GsonIgnore;

@Entity
@Table(name = "drivecredential")
@Audited
@Cacheable(value = true)
@XmlAccessorType(XmlAccessType.FIELD)
@SuppressWarnings("serial")
public class DriveCredential extends BaseModel
{
	@Id
	@SequenceGenerator(name = "id", sequenceName = "drivecredential_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "id")
	private Long driveCredentialID;

	@GsonIgnore
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userID")
	private User user;

	@Column(length = 100)
	private String accessToken;

	@Column(length = 100)
	private String refreshToken;

	public DriveCredential(User user, String accessToken, String refreshToken)
	{
		this.driveCredentialID = 0l;
		this.user = user;
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}

	public DriveCredential()
	{
		this(null, null, null);
	}

	@Override
	public Long getID()
	{
		return driveCredentialID;
	}

	@Override
	public void setID(Long id)
	{
		this.driveCredentialID = id;
	}

	public User getUser()
	{
		return user;
	}

	public void setUser(User user)
	{
		this.user = user;
	}

	public String getAccessToken()
	{
		return accessToken;
	}

	public void setAccessToken(String accessToken)
	{
		this.accessToken = accessToken;
	}

	public String getRefreshToken()
	{
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken)
	{
		this.refreshToken = refreshToken;
	}

	@Override
	public void valid() throws ValidationException
	{
		final MessageBuilder mb = new MessageBuilder();

		if (!mb.isEmpty())
		{
			throw new ValidationException(mb.getMessages());
		}
	}
}