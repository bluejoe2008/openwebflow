package org.openwebflow.conf;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.interceptor.SessionFactory;
import org.activiti.engine.impl.persistence.entity.GroupIdentityManager;
import org.activiti.engine.impl.persistence.entity.MembershipIdentityManager;
import org.activiti.engine.impl.persistence.entity.UserIdentityManager;
import org.openwebflow.identity.CustomEntityManagerFactory;
import org.openwebflow.identity.CustomMembershipManager;
import org.openwebflow.identity.impl.DummyGroupIdentityManager;
import org.openwebflow.identity.impl.DummyMembershipIdentityManager;
import org.openwebflow.identity.impl.DummyUserIdentityManager;

public class ReplaceMembershipManager implements StartEngineEventListener
{
	CustomMembershipManager _customMembershipManager;

	@Override
	public void afterStartEngine(ProcessEngineConfigurationImpl conf, ProcessEngine processEngine)
	{
	}

	@Override
	public void beforeStartEngine(ProcessEngineConfigurationImpl conf)
	{
		List<SessionFactory> sessionFactories = new ArrayList<SessionFactory>();
		sessionFactories.add(new CustomEntityManagerFactory(UserIdentityManager.class, new DummyUserIdentityManager(
				_customMembershipManager)));
		sessionFactories.add(new CustomEntityManagerFactory(GroupIdentityManager.class, new DummyGroupIdentityManager(
				_customMembershipManager)));
		sessionFactories.add(new CustomEntityManagerFactory(MembershipIdentityManager.class,
				new DummyMembershipIdentityManager()));

		conf.setCustomSessionFactories(sessionFactories);
	}

	public CustomMembershipManager getCustomMembershipManager()
	{
		return _customMembershipManager;
	}

	public void setCustomMembershipManager(CustomMembershipManager customMembershipManager)
	{
		_customMembershipManager = customMembershipManager;
	}

}
