package com.reelbook.model;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.hibernate.envers.Audited;
import com.reelbook.core.exception.ValidationException;
import com.reelbook.core.model.BaseModel;
import com.reelbook.core.msg.MessageBuilder;
import com.reelbook.core.util.CompareUtil;
import com.reelbook.model.enumeration.ProfileReservedEnum;

@Entity
@Table(name = "adonis_admin_user")
@Inheritance(strategy = InheritanceType.JOINED)
@Audited
@SuppressWarnings("serial")
public abstract class User extends BaseModel
{
	@Id
	@SequenceGenerator(name = "id", sequenceName = "adonis_admin_user_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "id")
	private Long userID;

	@Column(length = 100, unique = true)
	private String userName;

	@Column(length = 50)
	private String userPassword;

	@Basic
	private Boolean active;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "adonis_admin_user_profile", joinColumns = @JoinColumn(name = "userID"), inverseJoinColumns = @JoinColumn(name = "profileID"))
	private List<Profile> profiles;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "agentID")
	private SystemAgent systemAgent;

	/**
	 */
	protected User(String userName, String userPassword, List<Profile> profiles)
	{
		this.userID = 0l;
		this.userName = userName;
//		this.userPassword = CryptoUtil.createPasswordHash("MD5", "Base64", "UTF-8", userName, userPassword);
		this.active = true;
		this.profiles = profiles;
		this.systemAgent = null;
	}
	
	/**
	 */
	protected User()
	{
		this("", "", null);
	}

	/**
	 */
	@Override
	public Long getID()
	{
		return userID;
	}

	/**
	 */
	@Override
	public void setID(Long id)
	{
		this.userID = id;
	}

	/**
	 */
	public String getUserName()
	{
		return userName;
	}

	/**
	 */
	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	/**
	 */
	public String getUserPassword()
	{
		return userPassword;
	}

	/**
	 */
	public void setUserPassword(String pass)
	{
		this.userPassword = getPasswordHash(this.userName, pass);
	}

	/**
	 */
	public boolean checkUserPassword(String pass)
	{
		return this.userPassword.equals(getPasswordHash(this.userName, pass));
	}

	/**
	 */
	public Boolean getActive()
	{
		return active;
	}

	/**
	 */
	public void setActive(Boolean active)
	{
		this.active = active;
	}

	/**
	 */
	public List<Profile> getProfiles()
	{
		if (profiles == null)
		{
			profiles = new ArrayList<Profile>();
		}
		return profiles;
	}

	/**
	 */
	public void setProfiles(List<Profile> profiles)
	{
		this.profiles = profiles;
	}

	/**
	 */
	public SystemAgent getSystemAgent()
	{
		if (systemAgent == null)
		{
			systemAgent = new SystemAgent();
		}
		return systemAgent;
	}

	/**
	 */
	public void setSystemAgent(SystemAgent systemAgent)
	{
		this.systemAgent = systemAgent;
	}

	/**
	 */
	@Override
	public void initLazyElements()
	{
		super.initLazyElements();
		getProfiles().size();
	}

	/**
	 */
	@Override
	public void valid() throws ValidationException
	{
		final MessageBuilder mb = new MessageBuilder();

		try
		{
			getSystemAgent().valid();
		}
		catch (ValidationException v)
		{
			mb.addMessage(v.getMessages());
		}

		if (CompareUtil.isEmpty(getUserName()))
		{
//			mb.addMessage(DBSMsgHandler.getMsg(User.class, "userNameEmpty"));
		}
		else if (isNew() && isUserNameReserved())
		{
//			mb.addMessage(DBSMsgHandler.getMsg(User.class, "userNameInvalid"));
		}

		if (CompareUtil.isEmpty(getUserPassword()))
		{
//			mb.addMessage(DBSMsgHandler.getMsg(User.class, "userPasswordEmpty"));
		}

		if (CompareUtil.isEmpty(getProfiles()))
		{
//			mb.addMessage(DBSMsgHandler.getMsg(User.class, "profilesEmpty"));
		}

		if (!mb.isEmpty())
		{
			throw new ValidationException(mb.getMessages());
		}
	}

	/**
	 */
	public Boolean isUserNameReserved()
	{
//		return UserReservedEnum.getNames().contains(getUserName());
		return null;
	}

	/**
	 */
	public String getFullName()
	{
		final StringBuilder sb = new StringBuilder();

		if (!CompareUtil.isEmpty(getSystemAgent().getLastName()))
		{
			sb.append(getSystemAgent().getLastName().trim());
		}

		if (!CompareUtil.isEmpty(getSystemAgent().getFirstName()))
		{
			if (!CompareUtil.isEmpty(sb.toString()))
			{
				sb.append(", ");
			}
			sb.append(getSystemAgent().getFirstName().trim());
		}
		return sb.toString();
	}

	/**
	 */
	@Override
	public String getFullDescription()
	{
		final StringBuilder sb = new StringBuilder();

		if (!CompareUtil.isEmpty(getUserName()))
		{
			sb.append(getUserName().trim());
		}

		if (!CompareUtil.isEmpty(getFullName()))
		{
			if (!CompareUtil.isEmpty(sb.toString()))
			{
				sb.append(" - ");
			}
			sb.append(getFullName().trim());
		}
		return sb.toString();
	}

	/**
	 */
	public String getMembersDescription()
	{
		final StringBuilder sb = new StringBuilder();

		for (Iterator<Profile> it = getProfiles().iterator(); it.hasNext();)
		{
			final Profile profile = it.next();
			final String groupName = profile.getGroupName();
			if (!ProfileReservedEnum.BASIC.name().equals(groupName))
			{
				sb.append(groupName.trim());
				if (it.hasNext())
				{
					sb.append(" - ");
				}
			}
		}
		return sb.toString();
	}

	/**
	 */
	private String getPasswordHash(final String userName, final String pass)
	{
//		return CryptoUtil.createPasswordHash("MD5", "Base64", "UTF-8", userName, pass);
		return null;
	}
}