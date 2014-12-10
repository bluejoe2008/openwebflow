package org.openwebflow.assign.acl;

import java.util.HashMap;
import java.util.Map;

public class InMemoryActivityAclStore extends AbstractActivityAclStore implements ActivityAclManager
{
	Map<String, ActivityAclEntry> _entryMap = new HashMap<String, ActivityAclEntry>();

	private String getKey(String processDefId, String taskDefinitionKey)
	{
		return processDefId + "--" + taskDefinitionKey;
	}

	public void save(String processDefId, String taskDefinitionKey, String assignee, String[] candidateGroupIds,
			String[] candidateUserIds) throws Exception
	{
		ActivityAclEntryImpl entry = new ActivityAclEntryImpl();
		entry.setAssignee(assignee);
		entry.setGrantedGroupIds(candidateGroupIds);
		entry.setGrantedUserIds(candidateUserIds);

		_entryMap.put(getKey(processDefId, taskDefinitionKey), entry);
	}

	@Override
	public ActivityAclEntry load(String processDefinitionId, String taskDefinitionKey, boolean addOrRemove)
	{
		if (addOrRemove)
			return _entryMap.get(getKey(processDefinitionId, taskDefinitionKey));

		return null;
	}

	@Override
	public void removeAll()
	{
		_entryMap.clear();
	}
}
