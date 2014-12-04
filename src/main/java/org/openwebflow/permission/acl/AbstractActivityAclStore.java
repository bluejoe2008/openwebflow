package org.openwebflow.permission.acl;

public abstract class AbstractActivityAclStore implements ActivityAclManager
{
	public abstract void save(String processDefId, String taskDefinitionKey, String assignee,
			String[] candidateGroupIds, String[] candidateUserIds) throws Exception;

	public abstract void removeAll();
}