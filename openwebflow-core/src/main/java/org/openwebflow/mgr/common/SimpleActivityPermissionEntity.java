package org.openwebflow.mgr.common;

import java.sql.Date;

import org.openwebflow.assign.permission.ActivityPermissionEntity;

/**
 * 20141015于高空
 * 
 * @author bluejoe2008@gmail.com
 * 
 */
public class SimpleActivityPermissionEntity implements ActivityPermissionEntity
{
	private String _activityKey;

	private String _assignee;

	private String[] _grantedGroupIds;

	private String[] _grantedUserIds;

	private long _id;

	private Date _opTime;

	private String _processDefinitionId;

	public String getActivityKey()
	{
		return _activityKey;
	}

	public String getAssignee()
	{
		return _assignee;
	}

	public String[] getGrantedGroupIds()
	{
		return _grantedGroupIds;
	}

	public String[] getGrantedUserIds()
	{
		return _grantedUserIds;
	}

	public long getId()
	{
		return _id;
	}

	public Date getOpTime()
	{
		return _opTime;
	}

	public String getProcessDefinitionId()
	{
		return _processDefinitionId;
	}

	public void setActivityKey(String activityKey)
	{
		_activityKey = activityKey;
	}

	public void setAssignee(String assignee)
	{
		_assignee = assignee;
	}

	public void setGrantedGroupIds(String[] grantedGroupIds)
	{
		_grantedGroupIds = grantedGroupIds;
	}

	public void setGrantedUserIds(String[] grantedUserIds)
	{
		_grantedUserIds = grantedUserIds;
	}

	public void setId(long id)
	{
		_id = id;
	}

	public void setOpTime(Date time)
	{
		_opTime = time;
	}

	public void setProcessDefinitionId(String processDefinitionId)
	{
		_processDefinitionId = processDefinitionId;
	}
}
