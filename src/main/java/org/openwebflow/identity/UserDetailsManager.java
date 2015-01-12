package org.openwebflow.identity;

public interface UserDetailsManager
{
	UserDetailsEntity findUserDetails(String userId) throws Exception;
}
