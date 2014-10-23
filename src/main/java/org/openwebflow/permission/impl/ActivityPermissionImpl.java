package org.openwebflow.permission.impl;

import java.sql.Date;

import org.openwebflow.permission.ActivityPermission;

/**
 * 20141015于高空
 * 
 * @author bluejoe2008@gmail.com
 * 
 */
public class ActivityPermissionImpl implements ActivityPermission
{
	private String _activityId;

	private String _assignedUser;

	private String _grantedGroups;

	private String _grantedUsers;

	private long _id;

	private String _processDefId;

	private Date _opTime;

	public String getActivityId()
	{
		return _activityId;
	}

	public String getAssignedUser()
	{
		return _assignedUser;
	}

	public String getGrantedGroups()
	{
		return _grantedGroups;
	}

	public String getGrantedUsers()
	{
		return _grantedUsers;
	}

	public long getId()
	{
		return _id;
	}

	public Date getOpTime()
	{
		return _opTime;
	}

	public String getProcessDefId()
	{
		return _processDefId;
	}

	public void setActivityId(String activityId)
	{
		_activityId = activityId;
	}

	public void setAssignedUser(String assignedUser)
	{
		_assignedUser = assignedUser;
	}

	public void setGrantedGroups(String grantedGroups)
	{
		_grantedGroups = grantedGroups;
	}

	public void setGrantedUsers(String grantedUsers)
	{
		_grantedUsers = grantedUsers;
	}

	public void setId(long id)
	{
		_id = id;
	}

	public void setOpTime(Date time)
	{
		_opTime = time;
	}

	public void setProcessDefId(String processDefId)
	{
		_processDefId = processDefId;
	}
}
