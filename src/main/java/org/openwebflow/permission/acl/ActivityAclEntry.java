package org.openwebflow.permission.acl;

public interface ActivityAclEntry
{

	String getAssignee();

	String[] getGrantedGroupIds();

	String[] getGrantedUserIds();

}