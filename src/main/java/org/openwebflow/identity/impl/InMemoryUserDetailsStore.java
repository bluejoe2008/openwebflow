package org.openwebflow.identity.impl;

import java.util.HashMap;
import java.util.Map;

import org.openwebflow.identity.AbstractUserDetailsStore;
import org.openwebflow.identity.IdentityUserDetails;
import org.openwebflow.identity.UserDetailsManager;

public class InMemoryUserDetailsStore extends AbstractUserDetailsStore implements UserDetailsManager
{
	Map<String, IdentityUserDetails> _users = new HashMap<String, IdentityUserDetails>();

	public void saveUser(IdentityUserDetails userDetails)
	{
		_users.put(userDetails.getUserId(), userDetails);
	}

	@Override
	public IdentityUserDetails findUser(String userId)
	{
		return _users.get(userId);
	}
}
