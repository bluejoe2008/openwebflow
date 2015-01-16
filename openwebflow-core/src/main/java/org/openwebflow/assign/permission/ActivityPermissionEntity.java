package org.openwebflow.assign.permission;

public interface ActivityPermissionEntity
{

	String getAssignee();

	String[] getGrantedGroupIds();

	String[] getGrantedUserIds();

}