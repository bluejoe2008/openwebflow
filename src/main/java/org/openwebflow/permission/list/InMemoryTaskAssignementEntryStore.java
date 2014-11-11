package org.openwebflow.permission.list;

import java.util.HashMap;
import java.util.Map;


public class InMemoryTaskAssignementEntryStore implements TaskAssignementEntryManager
{
	Map<String, TaskAssignementEntry> _entryMap = new HashMap<String, TaskAssignementEntry>();

	private String getKey(String processDefId, String taskDefinitionKey)
	{
		return processDefId + "--" + taskDefinitionKey;
	}

	public void addEntry(String processDefId, String taskDefinitionKey, String assignee,
			String[] candidateGroupIds, String[] candidateUserIds) throws Exception
	{
		TaskAssignmentEntryImpl entry = new TaskAssignmentEntryImpl();
		entry.setAssignee(assignee);
		entry.setGrantedGroupIds(candidateGroupIds);
		entry.setGrantedUserIds(candidateUserIds);

		_entryMap.put(getKey(processDefId, taskDefinitionKey), entry);
	}

	@Override
	public TaskAssignementEntry load(String processDefinitionId, String taskDefinitionKey, boolean addOrRemove)
	{
		if (addOrRemove)
			return _entryMap.get(getKey(processDefinitionId, taskDefinitionKey));

		return null;
	}
}
