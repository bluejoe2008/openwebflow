package org.openwebflow.assign.delegation;

public abstract class AbstractDelegationStore implements DelegationDetailsManager
{
	public abstract void addDelegation(String delegated, String delegate);

	public abstract void removeAll();
}