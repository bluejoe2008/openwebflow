package org.openwebflow.identity;

public abstract class AbstractUserDetailsStore implements UserDetailsManager
{
	public abstract void saveUser(IdentityUserDetails userDetails);

	public abstract void removeAll();
}
