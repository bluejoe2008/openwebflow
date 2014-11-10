package org.openwebflow.identity.impl;

import java.util.HashMap;
import java.util.Map;

import org.openwebflow.identity.IdentityUserDetails;
import org.openwebflow.identity.UserDetailsService;

public class InMemoryUserDetailsStore implements UserDetailsService
{
	Map<String, IdentityUserDetails> _users = new HashMap<String, IdentityUserDetails>();

	public void add(IdentityUserDetails userDetails)
	{
		_users.put(userDetails.getUserId(), userDetails);
	}

	@Override
	public IdentityUserDetails getUserDetails(String userId)
	{
		return _users.get(userId);
	}
}
