package org.openwebflow.identity.impl;

import org.openwebflow.identity.IdentityMembershipManager;

public abstract class AbstractMembershipStore implements IdentityMembershipManager
{
	public abstract void saveMembership(String userId, String groupId);

	public abstract void removeAll();
}