package org.openwebflow.assign.delegation;

public abstract class AbstractDelegationStore implements DelegationDetailsManager
{
	public abstract void saveDelegation(String delegated, String delegate) throws Exception;

	public abstract void removeAll() throws Exception;
}