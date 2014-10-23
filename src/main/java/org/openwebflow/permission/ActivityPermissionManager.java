package org.openwebflow.permission;

import java.util.List;

public interface ActivityPermissionManager
{
	List<ActivityPermission> loadAll();

	ActivityPermission loadById(String processDefId, String activityId);
}