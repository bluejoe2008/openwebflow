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
import org.openwebflow.permission.ActivityPermissionServiceConfiguration;

public class ProcessEngineConfigurationEx extends SpringProcessEngineConfiguration
{
	ActivityPermissionServiceConfiguration _activityPermissionServiceConfiguration;

	CustomMembershipManager _customMembershipManager;

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
			if (_activityPermissionServiceConfiguration != null)
			{
				_activityPermissionServiceConfiguration.load(processEngine);
			}
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}

		return processEngine;
	}

	public ActivityPermissionServiceConfiguration getActivityPermissionServiceConfiguration()
	{
		return _activityPermissionServiceConfiguration;
	}

	public CustomMembershipManager getCustomMembershipManager()
	{
		return _customMembershipManager;
	}

	public void setActivityPermissionServiceConfiguration(
			ActivityPermissionServiceConfiguration activityPermissionServiceConfiguration)
	{
		_activityPermissionServiceConfiguration = activityPermissionServiceConfiguration;
	}

	public void setCustomMembershipManager(CustomMembershipManager customMembershipManager)
	{
		_customMembershipManager = customMembershipManager;
	}
}
