package org.openwebflow.permission.acl;

import java.util.HashMap;
import java.util.Map;

import org.openwebflow.permission.ActivityAcessControlEntry;

public class InMemoryActivityAccessControlList implements ActivityAccessControlList
{
	Map<String, ActivityAcessControlEntry> _entryMap = new HashMap<String, ActivityAcessControlEntry>();

	private String getKey(String processDefId, String taskDefinitionKey)
	{
		return processDefId + "--" + taskDefinitionKey;
	}

	public void addEntry(String processDefId, String taskDefinitionKey, String assignee,
			String[] candidateGroupIds, String[] candidateUserIds) throws Exception
	{
		ActivityAcessControlEntryImpl entry = new ActivityAcessControlEntryImpl();
		entry.setAssignee(assignee);
		entry.setGrantedGroupIds(candidateGroupIds);
		entry.setGrantedUserIds(candidateUserIds);

		_entryMap.put(getKey(processDefId, taskDefinitionKey), entry);
	}

	@Override
	public ActivityAcessControlEntry load(String processDefinitionId, String taskDefinitionKey, boolean addOrRemove)
	{
		if (addOrRemove)
			return _entryMap.get(getKey(processDefinitionId, taskDefinitionKey));

		return null;
	}
}
