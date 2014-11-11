package org.openwebflow.permission.list;

public interface TaskAssignementEntryManager
{
	TaskAssignementEntry load(String processDefinitionId, String taskDefinitionKey, boolean addOrRemove);
}