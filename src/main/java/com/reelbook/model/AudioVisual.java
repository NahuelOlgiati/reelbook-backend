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

@Entity
@Table(name = "audiovisual")
@Inheritance(strategy = InheritanceType.JOINED)
@Audited
@XmlAccessorType(XmlAccessType.FIELD)
@SuppressWarnings("serial")
public class AudioVisual extends BaseModel 
{
	@Id
	@SequenceGenerator(name = "id", sequenceName = "audiovisual_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "id")
	private Long audioVisualID;

	@Column(length = 100, unique = true)
	private String audioVisualName;

	@Column(length = 50)
	private String firstName;

	@Column(length = 50)
	private String lastName;

	@Column(length = 50)
	private String audioVisualPassword;

	@Column(length = 100, unique = true)
	private String email;
	
	@Basic
	private Long artistID;

	@Basic
	private Boolean validated;

	@Basic
	private Boolean active;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "audioVisual_profile", joinColumns = @JoinColumn(name = "audioVisualID"), inverseJoinColumns = @JoinColumn(name = "profileID"))
	private List<Profile> profiles;

	public AudioVisual(String audioVisualName, String firstName, String lastName, String audioVisualPassword, List<Profile> profiles) {
		this.audioVisualID = 0l;
		this.audioVisualName = audioVisualName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.audioVisualPassword = CryptoUtil.createPasswordHash("MD5", "Base64", "UTF-8", audioVisualName, audioVisualPassword);
		this.email = null;
		this.validated = false;
		this.active = true;
		this.profiles = profiles;
	}

	public AudioVisual() {
		this("", "", "", "", null);
	}

	@Override
	public Long getID() {
		return audioVisualID;
	}

	@Override
	public void setID(Long id) {
		this.audioVisualID = id;
	}

	public String getAudioVisualName() {
		return audioVisualName;
	}

	public void setAudioVisualName(String audioVisualName) {
		this.audioVisualName = audioVisualName;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAudioVisualPassword() {
		return audioVisualPassword;
	}

	public void setAudioVisualPassword(String pass) {
		this.audioVisualPassword = getPasswordHash(this.audioVisualName, pass);
	}

	public boolean checkAudioVisualPassword(String pass) {
		return this.audioVisualPassword.equals(getPasswordHash(this.audioVisualName, pass));
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public Long getArtistID() {
		return artistID;
	}

	public void setArtistID(Long id) {
		this.artistID = id;
	}

	public Boolean getValidated() {
		return validated;
	}

	public void setValidated(Boolean validated) {
		this.validated = validated;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public List<Profile> getProfiles() {
		if (profiles == null) {
			profiles = new ArrayList<Profile>();
		}
		return profiles;
	}

	public void setProfiles(List<Profile> profiles) {
		this.profiles = profiles;
	}

	@Override
	public void initLazyElements() {
		super.initLazyElements();
		getProfiles().size();
	}

	@Override
	public void valid() throws ValidationException {
		final MessageBuilder mb = new MessageBuilder();

		if (CompareUtil.isEmpty(getAudioVisualName())) {
			// mb.addMessage(DBSMsgHandler.getMsg(AudioVisual.class, "audioVisualNameEmpty"));
		} else if (isNew() /* && isAudioVisualNameReserved() */) {
			// mb.addMessage(DBSMsgHandler.getMsg(AudioVisual.class,
			// "audioVisualNameInvalid"));
		}

		if (CompareUtil.isEmpty(getAudioVisualPassword())) {
			// mb.addMessage(DBSMsgHandler.getMsg(AudioVisual.class,
			// "audioVisualPasswordEmpty"));
		}

		if (CompareUtil.isEmpty(getProfiles())) {
			// mb.addMessage(DBSMsgHandler.getMsg(AudioVisual.class, "profilesEmpty"));
		}

		if (!mb.isEmpty()) {
			throw new ValidationException(mb.getMessages());
		}
	}

	public Boolean isAudioVisualNameReserved() {
		// return AudioVisualReservedEnum.getNames().contains(getAudioVisualName());
		return null;
	}

	public String getFullName() {
		final StringBuilder sb = new StringBuilder();

		if (!CompareUtil.isEmpty(getLastName())) {
			sb.append(getLastName().trim());
		}

		if (!CompareUtil.isEmpty(getFirstName())) {
			if (!CompareUtil.isEmpty(sb.toString())) {
				sb.append(", ");
			}
			sb.append(getFirstName().trim());
		}
		return sb.toString();
	}

	@Override
	public String getFullDescription() {
		final StringBuilder sb = new StringBuilder();

		if (!CompareUtil.isEmpty(getAudioVisualName())) {
			sb.append(getAudioVisualName().trim());
		}

		if (!CompareUtil.isEmpty(getFullName())) {
			if (!CompareUtil.isEmpty(sb.toString())) {
				sb.append(" - ");
			}
			sb.append(getFullName().trim());
		}
		return sb.toString();
	}

	public String getMembersDescription() {
		final StringBuilder sb = new StringBuilder();

		for (Iterator<Profile> it = getProfiles().iterator(); it.hasNext();) {
			final Profile profile = it.next();
			final String groupName = profile.getGroupName();
			if (!ProfileReservedEnum.BASIC.name().equals(groupName)) {
				sb.append(groupName.trim());
				if (it.hasNext()) {
					sb.append(" - ");
				}
			}
		}
		return sb.toString();
	}

	private String getPasswordHash(final String audioVisualName, final String pass) {
		return CryptoUtil.createPasswordHash("MD5", "Base64", "UTF-8", audioVisualName, pass);
	}
}