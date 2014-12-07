package org.openwebflow.assign.delegation.sql;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openwebflow.assign.delegation.AbstractDelegationStore;
import org.openwebflow.assign.delegation.DelegationDetailsManager;

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
		for (DelegationDetails sde : _mapper.findByDelegated(delegated))
		{
			delegates.put(sde.getDelegate(), 0);
		}

		return delegates.keySet().toArray(new String[0]);
	}

	@Override
	public void addDelegation(String delegated, String delegate)
	{
		SqlDelegationEntity sde = new SqlDelegationEntity();
		sde.setDelegated(delegated);
		sde.setDelegate(delegate);
		sde.setOpTime(new Date(System.currentTimeMillis()));
		_mapper.saveDelegation(sde);
	}

	@Override
	public void removeAll()
	{
		_mapper.deleteAll();
	}

	@Override
	public List<DelegationDetails> getDelegationDetailsList()
	{
		List<DelegationDetails> list = new ArrayList<DelegationDetails>();
		Collections.addAll(list, _mapper.selectAll().toArray(new DelegationDetails[0]));
		return list;
	}

}
