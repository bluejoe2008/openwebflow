package org.openwebflow.permission.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openwebflow.permission.ActivityPermission;
import org.openwebflow.permission.ActivityPermissionManager;

public class InMemoryActivityPermissionManager implements ActivityPermissionManager
{
	Map<String, ActivityPermission> _permissions = new HashMap<String, ActivityPermission>();

	public void save(ActivityPermission ap) throws Exception
	{
		_permissions.put(getKey(ap.getProcessDefId(), ap.getActivityId()), ap);
	}

	private String getKey(String processDefId, String activityId)
	{
		return processDefId + "--" + activityId;
	}

	@Override
	public List<ActivityPermission> loadAll()
	{
		return new ArrayList<ActivityPermission>(_permissions.values());
	}

	@Override
	public ActivityPermission loadById(String processDefId, String activityId)
	{
		return _permissions.get(getKey(processDefId, activityId));
	}
}
