package org.openwebflow.parts.ext;

public interface ActivityPermissionManagerEx
{
	public abstract void removeAll() throws Exception;

	public abstract void save(String processDefId, String taskDefinitionKey, String assignee,
			String[] candidateGroupIds, String[] candidateUserIds) throws Exception;
}