package org.openwebflow.identity;

import java.util.List;

public interface IdentityMembershipManager
{
	/**
	 * 获取指定的用户所在的组ID列表
	 */
	public List<String> findGroupIdsByUser(String userId);

	/**
	 * 获取指定组的成员用户ID列表
	 */
	public List<String> findUserIdsByGroup(String groupId);
}