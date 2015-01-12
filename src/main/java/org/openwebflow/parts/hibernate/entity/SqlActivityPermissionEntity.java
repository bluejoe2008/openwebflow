package org.openwebflow.parts.hibernate.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.openwebflow.assign.permission.ActivityPermissionEntity;
import org.springframework.util.StringUtils;

@Entity
@Table(name = "OWF_ACTIVITY_ACL")
public class SqlActivityPermissionEntity implements ActivityPermissionEntity
{
	@Column(name = "ACTIVITY_KEY")
	private String _activityKey;

	@Column(name = "ASSIGNED_USER")
	private String _assignee;

	@Transient
	private String[] _grantedGroupIds;

	@Column(name = "GRANTED_GROUPS")
	private String _grantedGroupString;

	@Transient
	private String[] _grantedUserIds;

	@Column(name = "GRANTED_USERS")
	private String _grantedUserString;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private long _id;

	@Column(name = "OP_TIME")
	private Date _opTime;

	@Column(name = "PROCESS_DEF_ID")
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

	public String getGrantedGroupString()
	{
		return _grantedGroupString;
	}

	public String[] getGrantedUserIds()
	{
		return _grantedUserIds;
	}

	public String getGrantedUserString()
	{
		return _grantedUserString;
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
		_grantedGroupString = StringUtils.arrayToDelimitedString(grantedGroupIds, ";");
	}

	public void setGrantedGroupString(String grantedGroupString)
	{
		_grantedGroupString = grantedGroupString;
		_grantedGroupIds = StringUtils.delimitedListToStringArray(grantedGroupString, ";");
	}

	public void setGrantedUserIds(String[] grantedUserIds)
	{
		_grantedUserString = StringUtils.arrayToDelimitedString(grantedUserIds, ";");
	}

	public void setGrantedUserString(String grantedUserString)
	{
		_grantedUserString = grantedUserString;
		_grantedUserIds = StringUtils.delimitedListToStringArray(grantedUserString, ";");
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
