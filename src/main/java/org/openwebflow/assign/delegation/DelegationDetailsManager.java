package org.openwebflow.assign.delegation;

import java.util.List;


public interface DelegationDetailsManager
{
	String[] getDelegates(String delegated);

	List<DelegationDetails> getDelegationDetailsList();
}