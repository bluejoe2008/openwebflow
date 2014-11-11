package org.openwebflow.permission.list;

public interface TaskAssignementEntry
{

	String getAssignee();

	String[] getGrantedGroupIds();

	String[] getGrantedUserIds();

}