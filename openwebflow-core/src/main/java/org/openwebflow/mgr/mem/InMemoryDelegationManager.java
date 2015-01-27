package org.openwebflow.mgr.mem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openwebflow.assign.delegation.DelegationEntity;
import org.openwebflow.assign.delegation.DelegationManager;
import org.openwebflow.mgr.common.SimpleDelegationEntity;
import org.openwebflow.mgr.ext.DelegationManagerEx;

public class InMemoryDelegationManager implements DelegationManager, DelegationManagerEx
{
	List<DelegationEntity> _list = new ArrayList<DelegationEntity>();

	@Override
	public String[] getDelegates(String delegated)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		for (DelegationEntity item : _list)
		{
			if (delegated.equals(item.getDelegated()))
				map.put(item.getDelegate(), 0);
		}

		return map.keySet().toArray(new String[0]);
	}

	@Override
	public List<DelegationEntity> listDelegationEntities()
	{
		return new ArrayList<DelegationEntity>(_list);
	}

	public void remove(String delegated, String delegate)
	{
		_list.remove(new SimpleDelegationEntity(delegated, delegate));
	}

	@Override
	public void removeAll()
	{
		_list.clear();
	}

	public void saveDelegation(String delegated, String delegate)
	{
		_list.add(new SimpleDelegationEntity(delegated, delegate));
	}
}
