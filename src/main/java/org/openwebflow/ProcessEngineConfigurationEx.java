package org.openwebflow;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.form.AbstractFormType;
import org.activiti.engine.impl.interceptor.SessionFactory;
import org.activiti.engine.impl.persistence.entity.GroupIdentityManager;
import org.activiti.engine.impl.persistence.entity.MembershipIdentityManager;
import org.activiti.engine.impl.persistence.entity.UserIdentityManager;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.openwebflow.identity.CustomEntityManagerFactory;
import org.openwebflow.identity.CustomMembershipManager;
import org.openwebflow.identity.impl.DummyGroupIdentityManager;
import org.openwebflow.identity.impl.DummyMembershipIdentityManager;
import org.openwebflow.identity.impl.DummyUserIdentityManager;
import org.openwebflow.mvc.DummyFormType;
import org.openwebflow.permission.ActivityPermission;
import org.openwebflow.permission.ActivityPermissionManager;
import org.openwebflow.util.ActivityUtils;

public class ProcessEngineConfigurationEx extends SpringProcessEngineConfiguration
{
	ActivityPermissionManager _activityPermissionManager;

	public ActivityPermissionManager getActivityPermissionManager()
	{
		return _activityPermissionManager;
	}

	public void setActivityPermissionManager(ActivityPermissionManager activityPermissionManager)
	{
		_activityPermissionManager = activityPermissionManager;
	}

	CustomMembershipManager _customMembershipManager;

	boolean _loadPermissionsOnStartup = true;

	@Override
	public ProcessEngine buildProcessEngine()
	{
		//避免User类型报错
		List<AbstractFormType> types = new ArrayList<AbstractFormType>();
		types.add(new DummyFormType("user"));
		types.add(new DummyFormType("group"));
		super.setCustomFormTypes(types);

		if (_customMembershipManager != null)
		{
			List<SessionFactory> sessionFactories = new ArrayList<SessionFactory>();
			sessionFactories.add(new CustomEntityManagerFactory(UserIdentityManager.class,
					new DummyUserIdentityManager(_customMembershipManager)));
			sessionFactories.add(new CustomEntityManagerFactory(GroupIdentityManager.class,
					new DummyGroupIdentityManager(_customMembershipManager)));
			sessionFactories.add(new CustomEntityManagerFactory(MembershipIdentityManager.class,
					new DummyMembershipIdentityManager()));

			super.setCustomSessionFactories(sessionFactories);
		}

		ProcessEngine processEngine = super.buildProcessEngine();
		try
		{
			if (_loadPermissionsOnStartup)
			{
				loadAllPermissions(processEngine);
			}
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}

		return processEngine;
	}

	public CustomMembershipManager getCustomMembershipManager()
	{
		return _customMembershipManager;
	}

	public boolean isLoadPermissionsOnStartup()
	{
		return _loadPermissionsOnStartup;
	}

	private void loadAllPermissions(ProcessEngine processEngine) throws Exception
	{
		for (ActivityPermission ap : _activityPermissionManager.loadAll())
		{
			ActivityUtils.grantPermission(
				ActivityUtils.getActivity(processEngine, ap.getProcessDefId(), ap.getActivityId()),
				ap.getAssignedUser(), ap.getGrantedGroups(), ap.getGrantedUsers());
		}
	}

	public void setCustomMembershipManager(CustomMembershipManager customMembershipManager)
	{
		_customMembershipManager = customMembershipManager;
	}

	public void setLoadPermissionsOnStartup(boolean loadPermissionsOnStartup)
	{
		_loadPermissionsOnStartup = loadPermissionsOnStartup;
	}
}
