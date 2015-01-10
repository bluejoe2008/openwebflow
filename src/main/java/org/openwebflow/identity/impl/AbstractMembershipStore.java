package org.openwebflow.identity.impl;

import org.openwebflow.identity.IdentityMembershipManager;

public abstract class AbstractMembershipStore implements IdentityMembershipManager
{
	public abstract void saveMembership(String userId, String groupId) throws Exception;

	public abstract void removeAll() throws Exception;
}