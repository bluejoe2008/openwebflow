package org.openwebflow.parts.ext;

import org.openwebflow.identity.UserDetailsEntity;

public interface UserDetailsManagerEx
{
	public abstract void removeAll() throws Exception;

	public abstract void saveUserDetails(UserDetailsEntity userDetails) throws Exception;
}
