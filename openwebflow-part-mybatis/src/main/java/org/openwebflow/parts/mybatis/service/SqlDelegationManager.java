package org.openwebflow.parts.mybatis.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openwebflow.assign.delegation.DelegationEntity;
import org.openwebflow.assign.delegation.DelegationManager;
import org.openwebflow.parts.ext.DelegationManagerEx;
import org.openwebflow.parts.mybatis.entity.SqlDelegationEntity;
import org.openwebflow.parts.mybatis.mapper.SqlDelegationEntityMapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public class SqlDelegationManager extends SqlMapperBasedServiceBase<SqlDelegationEntityMapper> implements
		DelegationManager, DelegationManagerEx
{
	@Override
	public String[] getDelegates(String delegated)
	{
		Map<String, Object> delegates = new HashMap<String, Object>();
		for (DelegationEntity sde : _mapper.findByDelegated(delegated))
		{
			delegates.put(sde.getDelegate(), 0);
		}

		return delegates.keySet().toArray(new String[0]);
	}

	@Override
	public List<DelegationEntity> listDelegationEntities()
	{
		List<DelegationEntity> list = new ArrayList<DelegationEntity>();
		list.addAll(_mapper.list());
		return list;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void removeAll()
	{
		_mapper.deleteAll();
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void saveDelegation(String delegated, String delegate)
	{
		SqlDelegationEntity sde = new SqlDelegationEntity();
		sde.setDelegated(delegated);
		sde.setDelegate(delegate);
		sde.setOpTime(new Date(System.currentTimeMillis()));
		_mapper.saveDelegation(sde);
	}

}
