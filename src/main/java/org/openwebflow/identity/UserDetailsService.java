package org.openwebflow.identity;


public interface UserDetailsService
{

	IdentityUserDetails getUserDetails(String userId);

}
