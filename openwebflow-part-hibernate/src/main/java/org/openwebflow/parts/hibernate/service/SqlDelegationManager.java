package org.openwebflow.parts.hibernate.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openwebflow.assign.delegation.DelegationEntity;
import org.openwebflow.assign.delegation.DelegationManager;
import org.openwebflow.parts.ext.DelegationManagerEx;
import org.openwebflow.parts.hibernate.dao.SqlDelegationDao;
import org.openwebflow.parts.hibernate.entity.SqlDelegationEntity;
import org.openwebflow.util.OwfException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public class SqlDelegationManager implements DelegationManager, DelegationManagerEx
{
	@Autowired
	SqlDelegationDao _dao;

	@Override
	public String[] getDelegates(String delegated)
	{
		Map<String, Object> delegates = new HashMap<String, Object>();
		try
		{
			for (SqlDelegationEntity sde : _dao.findByDelegated(delegated))
			{
				delegates.put(sde.getDelegate(), 0);
			}
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}

		return delegates.keySet().toArray(new String[0]);
	}

	@Override
	public List<DelegationEntity> listDelegationEntities()
	{
		try
		{
			return _dao.list();
		}
		catch (Exception e)
		{
			throw new OwfException(e);
		}
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void removeAll() throws Exception
	{
		_dao.deleteAll();
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void saveDelegation(String delegated, String delegate) throws Exception
	{
		_dao.saveDelegation(delegated, delegate);
	}
}