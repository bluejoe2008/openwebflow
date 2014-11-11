package org.openwebflow.identity;

import java.util.List;

public interface IdentityMembershipManager
{
	public List<String> findGroupIdsByUser(String userId);

	public List<String> findUserIdsByGroup(String groupId);
}