package org.openwebflow.permission.delegation.sql;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import org.openwebflow.permission.delegation.AbstractDelegationStore;
import org.openwebflow.permission.delegation.DelegationDetailsManager;

public class SqlDelegationStore extends AbstractDelegationStore implements DelegationDetailsManager
{
	SqlDelegationEntityMapper _mapper;

	public SqlDelegationEntityMapper getMapper()
	{
		return _mapper;
	}

	public void setMapper(SqlDelegationEntityMapper mapper)
	{
		_mapper = mapper;
	}

	@Override
	public String[] getDelegates(String delegated)
	{
		Map<String, Object> delegates = new HashMap<String, Object>();
		for (SqlDelegationEntity sde : _mapper.findByDelegated(delegated))
		{
			for (String delegate : sde.getDelegates().split(";"))
			{
				delegates.put(delegate, new Object());
			}
		}

		return delegates.keySet().toArray(new String[0]);
	}

	@Override
	public void addDelegation(String delegated, String delegate)
	{
		SqlDelegationEntity sde = new SqlDelegationEntity();
		sde.setDelegated(delegated);
		sde.setDelegates(delegate);
		sde.setOpTime(new Date(System.currentTimeMillis()));
		_mapper.saveDelegation(sde);
	}

	@Override
	public void removeAll()
	{
		_mapper.deleteAll();
	}

}
