package org.openwebflow.assign.delegation;

import java.util.List;

import org.openwebflow.assign.delegation.sql.DelegationDetails;

public interface DelegationDetailsManager
{
	String[] getDelegates(String delegated);

	List<DelegationDetails> getDelegationDetailsList();
}