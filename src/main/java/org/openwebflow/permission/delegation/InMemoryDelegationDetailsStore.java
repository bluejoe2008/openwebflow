package org.openwebflow.permission.delegation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryDelegationDetailsStore extends AbstractDelegationStore  implements DelegationDetailsManager
{
	Map<String, List<String>> _map = new HashMap<String, List<String>>();

	public void addDelegation(String delegated, String delegate)
	{
		List<String> delegates = _map.get(delegated);
		if (delegates == null)
		{
			delegates = new ArrayList<String>();
			_map.put(delegated, delegates);
		}

		delegates.add(delegate);
	}

	public void remove(String delegated, String delegate)
	{
		List<String> delegates = _map.get(delegated);
		if (delegates != null)
		{
			delegates.remove(delegate);
		}
	}

	@Override
	public String[] getDelegates(String delegated)
	{
		List<String> delegates = _map.get(delegated);
		if (delegates == null)
			return new String[0];

		return delegates.toArray(new String[0]);
	}

}
