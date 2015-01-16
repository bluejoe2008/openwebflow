package org.openwebflow.assign.delegation;

import java.util.List;

public interface DelegationManager
{
	String[] getDelegates(String delegated);

	List<DelegationEntity> listDelegationEntities();
}