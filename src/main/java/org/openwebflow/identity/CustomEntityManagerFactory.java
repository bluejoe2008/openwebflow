package org.openwebflow.identity;

import org.activiti.engine.impl.interceptor.Session;
import org.activiti.engine.impl.interceptor.SessionFactory;

public class CustomEntityManagerFactory implements SessionFactory
{
	Class<?> _sessionType;

	public CustomEntityManagerFactory(Class<?> sessionType, Session entityManager)
	{
		super();
		_sessionType = sessionType;
		_entityManager = entityManager;
	}

	Session _entityManager;

	@Override
	public Class<?> getSessionType()
	{
		return _sessionType;
	}

	@Override
	public Session openSession()
	{
		return _entityManager;
	}
}
