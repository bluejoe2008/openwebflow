package org.openwebflow.assign.delegation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openwebflow.assign.delegation.sql.DelegationDetails;

public class InMemoryDelegationDetailsStore extends AbstractDelegationStore implements DelegationDetailsManager
{
	List<DelegationDetails> _list = new ArrayList<DelegationDetails>();

	public void addDelegation(String delegated, String delegate)
	{
		_list.add(new DelegationDetails(delegated, delegate));
	}

	@Override
	public String[] getDelegates(String delegated)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		for (DelegationDetails item : _list)
		{
			if (delegated.equals(item.getDelegated()))
				map.put(item.getDelegate(), 0);
		}

		return map.keySet().toArray(new String[0]);
	}

	@Override
	public List<DelegationDetails> getDelegationDetailsList()
	{
		return new ArrayList<DelegationDetails>(_list);
	}

	public void remove(String delegated, String delegate)
	{
		_list.remove(new DelegationDetails(delegated, delegate));
	}

	@Override
	public void removeAll()
	{
		_list.clear();
	}
}
