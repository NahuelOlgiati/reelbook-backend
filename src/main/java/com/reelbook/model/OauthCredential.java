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
import com.reelbook.core.util.CompareUtil;
import com.reelbook.rest.annotation.GsonIgnore;

@Entity
@Table(name = "oauthcredential")
@Audited
@Cacheable(value = true)
@XmlAccessorType(XmlAccessType.FIELD)
@SuppressWarnings("serial")
public class OauthCredential extends BaseModel
{
	@Id
	@SequenceGenerator(name = "id", sequenceName = "oauthcredential_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "id")
	private Long oauthCredentialID;

	@GsonIgnore
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userID")
	private User user;

	@Column(length = 150)
	private String youtubeAccessToken;

	@Column(length = 100)
	private String youtubeRefreshToken;

	@Column(length = 150)
	private String driveAccessToken;

	@Column(length = 100)
	private String driveRefreshToken;

	public OauthCredential(User user)
	{
		this.oauthCredentialID = 0l;
		this.user = user;
	}

	public OauthCredential()
	{
		this(null);
	}

	@Override
	public Long getID()
	{
		return oauthCredentialID;
	}

	@Override
	public void setID(Long id)
	{
		this.oauthCredentialID = id;
	}

	public User getUser()
	{
		return user;
	}

	public void setUser(User user)
	{
		this.user = user;
	}

	public String getYoutubeAccessToken()
	{
		return youtubeAccessToken;
	}

	public void setYoutubeAccessToken(String youtubeAccessToken)
	{
		this.youtubeAccessToken = youtubeAccessToken;
	}

	public String getYoutubeRefreshToken()
	{
		return youtubeRefreshToken;
	}

	public void setYoutubeRefreshToken(String youtubeRefreshToken)
	{
		this.youtubeRefreshToken = youtubeRefreshToken;
	}

	public String getDriveAccessToken()
	{
		return driveAccessToken;
	}

	public void setDriveAccessToken(String driveAccessToken)
	{
		this.driveAccessToken = driveAccessToken;
	}

	public String getDriveRefreshToken()
	{
		return driveRefreshToken;
	}

	public void setDriveRefreshToken(String driveRefreshToken)
	{
		this.driveRefreshToken = driveRefreshToken;
	}

	public Boolean hasYoutubeCredential()
	{
		return !CompareUtil.isEmpty(getYoutubeAccessToken()) && !CompareUtil.isEmpty(getYoutubeRefreshToken());
	}

	public Boolean hasDriveCredential()
	{
		return !CompareUtil.isEmpty(getDriveAccessToken()) && !CompareUtil.isEmpty(getDriveRefreshToken());
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