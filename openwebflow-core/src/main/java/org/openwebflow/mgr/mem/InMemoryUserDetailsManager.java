package org.openwebflow.mgr.mem;

import java.util.HashMap;
import java.util.Map;

import org.openwebflow.identity.UserDetailsEntity;
import org.openwebflow.identity.UserDetailsManager;
import org.openwebflow.mgr.ext.UserDetailsManagerEx;

public class InMemoryUserDetailsManager implements UserDetailsManager, UserDetailsManagerEx
{
	Map<String, UserDetailsEntity> _users = new HashMap<String, UserDetailsEntity>();

	@Override
	public UserDetailsEntity findUserDetails(String userId)
	{
		return _users.get(userId);
	}

	@Override
	public void removeAll()
	{
		_users.clear();
	}

	public void saveUserDetails(UserDetailsEntity userDetails)
	{
		_users.put(userDetails.getUserId(), userDetails);
	}
}
