package org.openwebflow.permission.delegation;

import java.util.List;

import org.openwebflow.permission.delegation.sql.DelegationDetails;

public interface DelegationDetailsManager
{
	String[] getDelegates(String delegated);

	List<DelegationDetails> getDelegationDetailsList();
}