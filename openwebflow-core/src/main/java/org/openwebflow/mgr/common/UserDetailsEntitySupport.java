package org.openwebflow.mgr.common;

import org.openwebflow.identity.UserDetailsEntity;

public abstract class UserDetailsEntitySupport implements UserDetailsEntity
{
	public void copyProperties(UserDetailsEntity src)
	{
		for (String name : src.getPropertyNames())
		{
			setProperty(name, src.getProperty(name));
		}
	}
}