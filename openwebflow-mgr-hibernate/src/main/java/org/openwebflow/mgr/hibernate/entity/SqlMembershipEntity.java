package org.openwebflow.mgr.hibernate.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "OWF_MEMBERSHIP")
public class SqlMembershipEntity
{
	@Column(name = "GROUP_ID")
	String _groupId;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	int _id;

	@Column(name = "USER_ID")
	String _userId;

	public String getGroupId()
	{
		return _groupId;
	}

	public int getId()
	{
		return _id;
	}

	public String getUserId()
	{
		return _userId;
	}

	public void setGroupId(String groupId)
	{
		_groupId = groupId;
	}

	public void setId(int id)
	{
		_id = id;
	}

	public void setUserId(String userId)
	{
		_userId = userId;
	}
}
