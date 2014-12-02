package org.openwebflow.permission.acl;

public interface ActivityAclManager
{
	ActivityAclEntry load(String processDefinitionId, String taskDefinitionKey, boolean addOrRemove);
}