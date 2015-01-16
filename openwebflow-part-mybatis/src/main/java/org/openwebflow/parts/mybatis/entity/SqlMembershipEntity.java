package org.openwebflow.parts.mybatis.entity;

public class SqlMembershipEntity
{
	String _groupId;

	int _id;

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
