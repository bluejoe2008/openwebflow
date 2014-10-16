package org.openwebflow.permission.impl;

import java.util.List;

import org.openwebflow.permission.ActivityPermission;
import org.openwebflow.permission.ActivityPermissionDao;
import org.openwebflow.permission.ActivityPermissionService;
import org.openwebflow.tool.ProcessEngineTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ActivityPermissionServiceImpl implements ActivityPermissionService
{
	private ActivityPermissionDao _permissionDao;

	@Autowired
	private ProcessEngineTool _processEngineEx;

	private ActivityPermissionDao getPermissionDao()
	{
		return _permissionDao;
	}

	@Override
	public List<ActivityPermission> list()
	{
		return getPermissionDao().list();
	}

	public void setPermissionDao(ActivityPermissionDao permissionDao)
	{
		_permissionDao = permissionDao;
	}

	@Override
	public void update(ActivityPermission ap) throws Exception
	{
		//先删除
		getPermissionDao().delete(ap);
		getPermissionDao().insertOrUpdate(ap);
		//同步更新活动的权限
		_processEngineEx.createActivityTool(ap.getProcessDefId(), ap.getActivityId()).grantPermission(
			ap.getAssignedUser(), ap.getGrantedGroups(), ap.getGrantedUsers());
	}
}
