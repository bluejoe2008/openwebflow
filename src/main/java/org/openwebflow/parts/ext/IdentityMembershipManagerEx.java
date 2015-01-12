package org.openwebflow.parts.ext;

public interface IdentityMembershipManagerEx
{
	public abstract void removeAll() throws Exception;

	public abstract void saveMembership(String userId, String groupId) throws Exception;
}