package org.openwebflow;

import java.util.ArrayList;
import java.util.List;

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
import org.springframework.beans.factory.InitializingBean;

public class ProcessEngineConfiguration extends SpringProcessEngineConfiguration implements InitializingBean
{
	CustomMembershipManager _customMembershipManager;

	@Override
	public void afterPropertiesSet() throws Exception
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
