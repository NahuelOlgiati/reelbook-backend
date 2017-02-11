package com.reelbook.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import org.hibernate.envers.Audited;
import org.jboss.crypto.CryptoUtil;
import com.reelbook.core.exception.ValidationException;
import com.reelbook.core.model.BaseModel;
import com.reelbook.core.msg.MessageBuilder;
import com.reelbook.core.util.CompareUtil;
import com.reelbook.model.enumeration.ProfileReservedEnum;
import com.reelbook.rest.annotation.GsonIgnore;

@Entity
@Table(name = "basic_user")
@Inheritance(strategy = InheritanceType.JOINED)
@Audited
@XmlAccessorType(XmlAccessType.FIELD)
@SuppressWarnings("serial")
public class User extends BaseModel
{
	@Id
	@SequenceGenerator(name = "id", sequenceName = "basic_user_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "id")
	private Long userID;

	@Column(length = 100, unique = true)
	private String userName;

	@Column(length = 50)
	private String firstName;

	@Column(length = 50)
	private String lastName;

	@Column(length = 50)
	private String userPassword;

	@Column(length = 100, unique = true)
	private String email;

	@Basic
	private Long artistID;

	@Basic
	private Long audioVisualID;

	@GsonIgnore
	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private YoutubeCredential youtubeCredential;

	@Basic
	private Boolean validated;

	@Basic
	private Boolean active;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_profile", joinColumns = @JoinColumn(name = "userID"), inverseJoinColumns = @JoinColumn(name = "profileID"))
	private List<Profile> profiles;

	public User(String userName, String firstName, String lastName, String userPassword, List<Profile> profiles)
	{
		this.userID = 0l;
		this.userName = userName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userPassword = CryptoUtil.createPasswordHash("MD5", "Base64", "UTF-8", userName, userPassword);
		this.email = null;
		this.validated = false;
		this.active = true;
		this.profiles = profiles;
	}

	public User()
	{
		this("", "", "", "", null);
	}

	@Override
	public Long getID()
	{
		return userID;
	}

	@Override
	public void setID(Long id)
	{
		this.userID = id;
	}

	public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public String getLastName()
	{
		return lastName;
	}

	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	public String getUserPassword()
	{
		return userPassword;
	}

	public void setUserPassword(String pass)
	{
		this.userPassword = getPasswordHash(this.userName, pass);
	}

	public boolean checkUserPassword(String pass)
	{
		return this.userPassword.equals(getPasswordHash(this.userName, pass));
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public Long getArtistID()
	{
		return artistID;
	}

	public void setArtistID(Long id)
	{
		this.artistID = id;
	}

	public Long getAudioVisualID()
	{
		return audioVisualID;
	}

	public void setAudioVisualID(Long audioVisualID)
	{
		this.audioVisualID = audioVisualID;
	}

	public YoutubeCredential getYoutubeCredential()
	{
		return youtubeCredential;
	}

	public void setYoutubeCredential(YoutubeCredential youtubeCredential)
	{
		this.youtubeCredential = youtubeCredential;
	}

	public Boolean getValidated()
	{
		return validated;
	}

	public void setValidated(Boolean validated)
	{
		this.validated = validated;
	}

	public Boolean getActive()
	{
		return active;
	}

	public void setActive(Boolean active)
	{
		this.active = active;
	}

	public List<Profile> getProfiles()
	{
		if (profiles == null)
		{
			profiles = new ArrayList<Profile>();
		}
		return profiles;
	}

	public void setProfiles(List<Profile> profiles)
	{
		this.profiles = profiles;
	}

	@Override
	public void initLazyElements()
	{
		super.initLazyElements();
		getProfiles().size();
	}

	@Override
	public void valid() throws ValidationException
	{
		final MessageBuilder mb = new MessageBuilder();

		if (CompareUtil.isEmpty(getUserName()))
		{
			// mb.addMessage(DBSMsgHandler.getMsg(User.class, "userNameEmpty"));
		}
		else if (isNew() /* && isUserNameReserved() */)
		{
			// mb.addMessage(DBSMsgHandler.getMsg(User.class,
			// "userNameInvalid"));
		}

		if (CompareUtil.isEmpty(getUserPassword()))
		{
			// mb.addMessage(DBSMsgHandler.getMsg(User.class,
			// "userPasswordEmpty"));
		}

		if (CompareUtil.isEmpty(getProfiles()))
		{
			// mb.addMessage(DBSMsgHandler.getMsg(User.class, "profilesEmpty"));
		}

		if (!mb.isEmpty())
		{
			throw new ValidationException(mb.getMessages());
		}
	}

	public Boolean isUserNameReserved()
	{
		// return UserReservedEnum.getNames().contains(getUserName());
		return null;
	}

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
				sb.append(", ");
			}
			sb.append(getFirstName().trim());
		}
		return sb.toString();
	}

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

	private String getPasswordHash(final String userName, final String pass)
	{
		return CryptoUtil.createPasswordHash("MD5", "Base64", "UTF-8", userName, pass);
	}
}