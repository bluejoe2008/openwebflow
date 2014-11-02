package org.openwebflow.permission.acl;

import org.openwebflow.permission.ActivityAcessControlEntry;

public interface ActivityAccessControlList
{
	ActivityAcessControlEntry load(String processDefinitionId, String taskDefinitionKey, boolean addOrRemove);
}