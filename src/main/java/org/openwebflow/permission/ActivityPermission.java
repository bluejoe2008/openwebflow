package org.openwebflow.permission;

import java.sql.Date;

public interface ActivityPermission
{

	String getActivityId();

	String getAssignedUser();

	String getGrantedGroups();

	String getGrantedUsers();

	Date getOpTime();

	String getProcessDefId();

	void setOpTime(Date date);

}