package org.openwebflow.permission.delegation.sql;

import java.util.HashMap;
import java.util.Map;

import org.openwebflow.permission.delegation.DelegationDetailsManager;

public class SqlDelegationStore implements DelegationDetailsManager
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

}
