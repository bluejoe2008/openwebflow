package org.openwebflow.assign.acl;

public interface ActivityAclManager
{
	ActivityAclEntry load(String processDefinitionId, String taskDefinitionKey, boolean addOrRemove);
}