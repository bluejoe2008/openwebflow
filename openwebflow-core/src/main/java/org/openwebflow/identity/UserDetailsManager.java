package org.openwebflow.identity;

public interface UserDetailsManager
{
	/**
	 * 根据用户名获取用户详细信息
	 */
	UserDetailsEntity findUserDetails(String userId) throws Exception;
}
