package org.openwebflow.identity;

import java.util.List;

public interface IdentityMembershipService
{
	public List<String> findGroupIdsByUser(String userId);

	public List<String> findUserIdsByGroup(String groupId);
}