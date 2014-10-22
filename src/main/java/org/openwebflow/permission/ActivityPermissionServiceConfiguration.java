package org.openwebflow.permission;

import org.activiti.engine.ProcessEngine;
import org.openwebflow.util.ActivityUtils;

public class ActivityPermissionServiceConfiguration
{
	ActivityPermissionService _activityPermissionService;

	boolean _loadPermissionsOnStartup = true;

	public ActivityPermissionService getActivityPermissionService()
	{
		return _activityPermissionService;
	}

	public boolean isLoadPermissionsOnStartup()
	{
		return _loadPermissionsOnStartup;
	}

	public void load(ProcessEngine processEngine) throws Exception
	{
		if (_loadPermissionsOnStartup)
		{
			loadAllPermissions(processEngine);
		}
	}

	private void loadAllPermissions(ProcessEngine processEngine) throws Exception
	{
		for (ActivityPermission ap : _activityPermissionService.list())
		{
			ActivityUtils.grantPermission(
				ActivityUtils.getActivity(processEngine, ap.getProcessDefId(), ap.getActivityId()),
				ap.getAssignedUser(), ap.getGrantedGroups(), ap.getGrantedUsers());
		}
	}

	public void setActivityPermissionService(ActivityPermissionService activityPermissionService)
	{
		_activityPermissionService = activityPermissionService;
	}

	public void setLoadPermissionsOnStartup(boolean loadPermissionsOnStartup)
	{
		_loadPermissionsOnStartup = loadPermissionsOnStartup;
	}
}
