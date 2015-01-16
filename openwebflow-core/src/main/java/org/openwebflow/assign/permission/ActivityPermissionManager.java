package org.openwebflow.assign.permission;

public interface ActivityPermissionManager
{
	ActivityPermissionEntity load(String processDefinitionId, String taskDefinitionKey, boolean addOrRemove)
			throws Exception;
}