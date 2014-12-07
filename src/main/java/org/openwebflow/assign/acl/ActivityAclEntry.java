package org.openwebflow.assign.acl;

public interface ActivityAclEntry
{

	String getAssignee();

	String[] getGrantedGroupIds();

	String[] getGrantedUserIds();

}