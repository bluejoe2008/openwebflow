package org.openwebflow.identity;

public interface UserDetailsManager
{

	IdentityUserDetails findUser(String userId);

}
