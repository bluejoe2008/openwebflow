package org.openwebflow.mgr.mem;

import java.util.HashMap;
import java.util.Map;

import org.openwebflow.assign.permission.ActivityPermissionEntity;
import org.openwebflow.assign.permission.ActivityPermissionManager;
import org.openwebflow.mgr.common.SimpleActivityPermissionEntity;
import org.openwebflow.mgr.ext.ActivityPermissionManagerEx;

public class InMemoryActivityPermissionManager implements ActivityPermissionManager, ActivityPermissionManagerEx
{
	Map<String, ActivityPermissionEntity> _entryMap = new HashMap<String, ActivityPermissionEntity>();

	private String getKey(String processDefId, String taskDefinitionKey)
	{
		return processDefId + "--" + taskDefinitionKey;
	}

	@Override
	public ActivityPermissionEntity load(String processDefinitionId, String taskDefinitionKey, boolean addOrRemove)
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

	public void save(String processDefId, String taskDefinitionKey, String assignee, String[] candidateGroupIds,
			String[] candidateUserIds) throws Exception
	{
		SimpleActivityPermissionEntity entry = new SimpleActivityPermissionEntity();
		entry.setAssignee(assignee);
		entry.setGrantedGroupIds(candidateGroupIds);
		entry.setGrantedUserIds(candidateUserIds);

		_entryMap.put(getKey(processDefId, taskDefinitionKey), entry);
	}
}
