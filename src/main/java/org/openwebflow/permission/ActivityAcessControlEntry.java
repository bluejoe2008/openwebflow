package org.openwebflow.permission;

public interface ActivityAcessControlEntry
{

	String getAssignee();

	String[] getGrantedGroupIds();

	String[] getGrantedUserIds();

}