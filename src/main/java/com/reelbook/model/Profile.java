package com.reelbook.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.hibernate.envers.Audited;
import com.reelbook.core.exception.ValidationException;
import com.reelbook.core.model.BaseModel;
import com.reelbook.core.msg.MessageBuilder;
import com.reelbook.core.util.CompareUtil;

@Entity
@Table(name = "profile")
@Audited
@SuppressWarnings("serial")
public class Profile extends BaseModel
{
	@Id
	@SequenceGenerator(name = "id", sequenceName = "profile_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "id")
	private Long profileID;

	@Column(length = 100, unique = true)
	private String groupName;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name = "profile_permit",
			joinColumns = @JoinColumn(name = "profileID"),
			inverseJoinColumns = @JoinColumn(name = "permitID"))
	private List<Permit> permits;

	public Profile(String groupName, List<Permit> permits)
	{
		this.profileID = 0l;
		this.groupName = groupName;
		this.permits = permits;
	}

	public Profile()
	{
		this(null, null);
	}

	@Override
	public Long getID()
	{
		return profileID;
	}

	@Override
	public void setID(Long id)
	{
		this.profileID = id;
	}

	public String getGroupName()
	{
		return groupName;
	}

	public void setGroupName(String groupName)
	{
		this.groupName = groupName;
	}

	public List<Permit> getPermits()
	{
		if (permits == null)
		{
			permits = new ArrayList<Permit>();
		}
		return permits;
	}

	public List<Permit> getSortedPermits()
	{
		Collections.sort(getPermits(), new Comparator<Permit>()
		{
			@Override
			public int compare(final Permit p1, final Permit p2)
			{
				int c;
				c = p1.getModule().compareTo(p2.getModule());
				if (c == 0)
				{
					c = p1.getDescription().compareTo(p2.getDescription());
				}
				return c;
			}
		});
		return getPermits();
	}

	public void setPermits(List<Permit> permits)
	{
		this.permits = permits;
	}

	@Override
	public void valid() throws ValidationException
	{

		final MessageBuilder mb = new MessageBuilder();

		if (CompareUtil.isEmpty(getGroupName()))
		{
			// mb.addMessage(DBSMsgHandler.getMsg(Profile.class, "groupNameEmpty"));
		}

		if (CompareUtil.isEmpty(getPermits()))
		{
			// mb.addMessage(DBSMsgHandler.getMsg(Profile.class, "permitsEmpty"));
		}

		if (!mb.isEmpty())
		{
			throw new ValidationException(mb.getMessages());
		}
	}
}