package org.openwebflow.conf;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.interceptor.SessionFactory;
import org.activiti.engine.impl.persistence.entity.GroupIdentityManager;
import org.activiti.engine.impl.persistence.entity.MembershipIdentityManager;
import org.activiti.engine.impl.persistence.entity.UserIdentityManager;
import org.openwebflow.identity.SessionedEntityManagerFactory;
import org.openwebflow.identity.IdentityMembershipService;
import org.openwebflow.identity.impl.DummyGroupIdentityManager;
import org.openwebflow.identity.impl.DummyMembershipIdentityManager;
import org.openwebflow.identity.impl.DummyUserIdentityManager;

public class ReplaceMembershipService implements StartEngineEventListener
{
	IdentityMembershipService _customMembershipService;

	public IdentityMembershipService getCustomMembershipService()
	{
		return _customMembershipService;
	}

	public void setCustomMembershipService(IdentityMembershipService customMembershipService)
	{
		_customMembershipService = customMembershipService;
	}

	@Override
	public void afterStartEngine(ProcessEngineConfigurationImpl conf, ProcessEngine processEngine)
	{
	}

	@Override
	public void beforeStartEngine(ProcessEngineConfigurationImpl conf)
	{
		List<SessionFactory> sessionFactories = new ArrayList<SessionFactory>();
		sessionFactories.add(new SessionedEntityManagerFactory(UserIdentityManager.class, new DummyUserIdentityManager(
				_customMembershipService)));
		sessionFactories.add(new SessionedEntityManagerFactory(GroupIdentityManager.class, new DummyGroupIdentityManager(
				_customMembershipService)));
		sessionFactories.add(new SessionedEntityManagerFactory(MembershipIdentityManager.class,
				new DummyMembershipIdentityManager()));

		conf.setCustomSessionFactories(sessionFactories);
	}

}
