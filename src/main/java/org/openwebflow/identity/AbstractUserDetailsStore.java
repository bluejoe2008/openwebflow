package org.openwebflow.identity;

public abstract class AbstractUserDetailsStore implements UserDetailsManager
{
	public abstract void saveUser(IdentityUserDetails userDetails) throws Exception;

	public abstract void removeAll() throws Exception;
}
