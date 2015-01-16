package org.openwebflow.identity.impl;

import org.activiti.engine.impl.interceptor.Session;
import org.activiti.engine.impl.persistence.entity.MembershipIdentityManager;

public class DummyMembershipIdentityManager implements MembershipIdentityManager, Session
{

	@Override
	public void close()
	{
	}

	@Override
	public void createMembership(String userId, String groupId)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteMembership(String userId, String groupId)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void flush()
	{
	}

}
