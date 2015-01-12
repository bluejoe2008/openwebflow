package org.openwebflow.parts.ext;

public interface DelegationManagerEx
{
	public abstract void removeAll() throws Exception;

	public abstract void saveDelegation(String delegated, String delegate) throws Exception;
}