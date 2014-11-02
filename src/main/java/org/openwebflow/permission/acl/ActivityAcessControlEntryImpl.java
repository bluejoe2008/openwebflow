package org.openwebflow.permission.acl;

import java.sql.Date;

import org.openwebflow.permission.ActivityAcessControlEntry;

/**
 * 20141015于高空
 * 
 * @author bluejoe2008@gmail.com
 * 
 */
public class ActivityAcessControlEntryImpl implements ActivityAcessControlEntry
{
	private String _activityKey;

	public String getActivityKey()
	{
		return _activityKey;
	}

	public void setActivityKey(String activityKey)
	{
		_activityKey = activityKey;
	}

	private String _assignee;

	public String getAssignee()
	{
		return _assignee;
	}

	public void setAssignee(String assignee)
	{
		_assignee = assignee;
	}

	public String[] getGrantedGroupIds()
	{
		return _grantedGroupIds;
	}

	public void setGrantedGroupIds(String[] grantedGroupIds)
	{
		_grantedGroupIds = grantedGroupIds;
	}

	public String[] getGrantedUserIds()
	{
		return _grantedUserIds;
	}

	public void setGrantedUserIds(String[] grantedUserIds)
	{
		_grantedUserIds = grantedUserIds;
	}

	private String[] _grantedGroupIds;

	private String[] _grantedUserIds;

	private long _id;

	private Date _opTime;

	private String _processDefinitionId;

	public String getProcessDefinitionId()
	{
		return _processDefinitionId;
	}

	public void setProcessDefinitionId(String processDefinitionId)
	{
		_processDefinitionId = processDefinitionId;
	}

	public long getId()
	{
		return _id;
	}

	public Date getOpTime()
	{
		return _opTime;
	}

	public void setId(long id)
	{
		_id = id;
	}

	public void setOpTime(Date time)
	{
		_opTime = time;
	}
}
