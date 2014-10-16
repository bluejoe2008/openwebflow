package org.openwebflow.permission.impl;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.openwebflow.permission.ActivityPermission;

/**
 * 20141015于高空
 * 
 * @author bluejoe2008@gmail.com
 * 
 */
@Entity
@Table(name = "ACTIVITY_PERMISSION_TAB")
public class ActivityPermissionImpl implements ActivityPermission
{

	@Column(name = "ACTIVITY_ID")
	private String _activityId;

	@Column(name = "ASSIGNED_USER")
	private String _assignedUser;

	@Column(name = "GRANTED_GROUPS")
	private String _grantedGroups;

	@Column(name = "GRANTED_USERS")
	private String _grantedUsers;

	@Id
	@Column(name = "ID")
	private long _id;

	@Column(name = "PROCESS_DEF_ID")
	private String _processDefId;

	@Column(name = "OP_TIME")
	private Date _time;

	@Override
	public String getActivityId()
	{
		return _activityId;
	}

	@Override
	public String getAssignedUser()
	{
		return _assignedUser;
	}

	@Override
	public String getGrantedGroups()
	{
		return _grantedGroups;
	}

	@Override
	public String getGrantedUsers()
	{
		return _grantedUsers;
	}

	public long getId()
	{
		return _id;
	}

	@Override
	public Date getOpTime()
	{
		return _time;
	}

	@Override
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
		_time = time;
	}

	public void setProcessDefId(String processDefId)
	{
		_processDefId = processDefId;
	}
}
