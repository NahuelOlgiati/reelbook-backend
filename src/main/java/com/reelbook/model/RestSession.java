package com.reelbook.model;

import java.util.Date;
import java.util.UUID;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.envers.Audited;
import com.reelbook.core.model.BaseModel;
import com.reelbook.core.msg.MessageBuilder;
import com.reelbook.core.util.CompareUtil;
import com.reelbook.service.msg.DBSMsgHandler;

@Entity
@Table(name = "parking_core_restsession")
@Audited
@SuppressWarnings("serial")
public class RestSession extends BaseModel
{
	@Id
	@SequenceGenerator(name = "restSessionID", sequenceName = "parking_core_restsession_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "restSessionID")
	private Long restSessionID;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "userID")
	private User user;

	@Temporal(TemporalType.TIMESTAMP)
	private Date lastAccess;

	@Column(length = 100)
	private String token;

	@Column(length = 255)
	private String agent;

	@Column(length = 25)
	private String IP;

	@Basic
	private Boolean expires;

	public RestSession(User user, String agent, String IP, Boolean expires)
	{
		this.restSessionID = 0l;
		this.user = user;
		this.lastAccess = new Date();
		this.token = UUID.randomUUID().toString();
		this.IP = IP;
		this.expires = expires;
	}

	public RestSession()
	{
		this(null, null, null, null);
	}

	@Override
	public final Long getID()
	{
		return restSessionID;
	}

	@Override
	public final void setID(Long id)
	{
		this.restSessionID = id;
	}

	public User getUser()
	{
		return user;
	}

	public Date getLastAccess()
	{
		return lastAccess;
	}

	public String getToken()
	{
		return token;
	}

	public String getAgent()
	{
		return agent;
	}

	public String getIP()
	{
		return IP;
	}

	public Boolean getExpires()
	{
		return expires;
	}

	public void updateLastAccess()
	{
		this.lastAccess = new Date();
	}

	@Override
	public void valid()
	{
		final MessageBuilder mb = new MessageBuilder();

		if (CompareUtil.isEmpty(getUser()))
		{
			mb.addMessage(DBSMsgHandler.getMsg(getClass(), "userEmpty"));
		}

		if (CompareUtil.isEmpty(getToken()))
		{
			mb.addMessage(DBSMsgHandler.getMsg(getClass(), "tokenEmpty"));
		}

		if (getExpires() == null)
		{
			mb.addMessage(DBSMsgHandler.getMsg(getClass(), "expiresEmpty"));
		}
	}
}